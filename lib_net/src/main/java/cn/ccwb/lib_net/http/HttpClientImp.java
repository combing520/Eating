package cn.ccwb.lib_net.http;

import android.net.ParseException;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by yyg on 2018/11/21 ,14:06
 */
public class HttpClientImp implements IHttpClient {
    public static final String TAG = HttpClientImp.class.getSimpleName();
    private static volatile HttpClientImp sHttpClient;
    private static Gson mGson;
    private IHttpService mIHttpService;

    private HttpClientImp() {
        mIHttpService = APIHelper.getInstance().createService(IHttpService.class);
        mGson = new GsonBuilder().create();
    }

    public static HttpClientImp getInstance() {
        if (sHttpClient == null) {
            synchronized (HttpClientImp.class) {
                if (sHttpClient == null) {
                    sHttpClient = new HttpClientImp();
                }
            }
        }
        return sHttpClient;
    }

    //错误处理
    private static void errorHandle(Throwable t) {
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "网络请求超时";
        } else if (t instanceof HttpException) {
            msg = convertStatusCode((HttpException) t);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        } else {
            //错误信息提示
            msg = t.getMessage();
        }
        LogUtils.e(TAG, "errorHandle  " + t.getMessage() + " " + t.getCause() + " msg = " + msg);
    }

    private static String convertStatusCode(HttpException httpException) {
        String msg;
        int code = httpException.code();
        if (code == 500) {
            msg = "服务器发生错误";
        } else if (code == 404) {
            msg = "请求地址不存在";
        } else if (code == 403) {
            msg = "请求被服务器拒绝";
        } else if (code == 401) {
            msg = "认证失败或登陆过期";
            //去登陆
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ARouter.getInstance().build("/account/login")
                            .navigation();
                }
            }, 500);

        } else {
            msg = "位置错误";
        }
        return msg;
    }

    private static boolean isValidJson(String content) {
        try {
            new JSONObject(content);
            return true;
        } catch (JSONException e) {
            return false;
        }

    }

    @Override
    public <T> Observable<T> post(String url, Map params, final Class<T> cls) {
        return mIHttpService.post(url, params)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null && json != "null") {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    //post返回String字符串（data为String时）
    @Override
    public <T> Observable postString(String url, Map params, final Class<T> cls) {
        return mIHttpService.post(url, params)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            return (ObservableSource<T>) Observable.just(json);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> postWithQueryBody(String url, Map params, Object body, final Class<T> cls) {
        return mIHttpService.postWithQueryBody(url, params, body)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    public void logI(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            LogUtils.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        LogUtils.i(tag, msg);
    }

    //参数在body中
    @Override
    public <T> Observable<T> postWithBody(String url, Object body, final Class<T> cls) {
        return mIHttpService.postWithBody(url, body)
                .subscribeOn(Schedulers.newThread())
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
//                        Log.d("postWithBody", json);
                        if (json != null && !TextUtils.isEmpty(json) && !json.equals("null")) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> postWithFormBody(String url, Object[] body, Class<T> cls) {

        return null;
    }

    /**
     * @param url     上传地址
     * @param fileMap 文件名和文件地址
     * @param cls     返回实体类
     * @param <T>
     * @return
     */
    @Override
    public <T> Observable<T> upload(String url, Map<String, String> fileMap, Class<T> cls) {
        Map<String, RequestBody> map = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : fileMap.entrySet()) {
//            RequestBody requestBody = RequestBody.create()
        }

//        RequestBody.create(MediaType.parse("*/*"), new File("filePath"));
//
//        return mIHttpService.upload("https://www.baidu.com",map)


        return null;
    }

    //上传文件

    /**
     * 文件下载
     *
     * @param url
     * @param cls
     * @param <T>
     * @return
     */
    @Override
    public <T> Observable<T> download(String url, Class<T> cls) {
        return null;
    }

    @Override
    public Observable postList(String url, Object body, final Type type) {
        return mIHttpService.postWithBody(url, body)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<? extends ArrayList>>() {
                    @Override
                    public ObservableSource<ArrayList> apply(String json) throws Exception {
                        if (json != null && !TextUtils.isEmpty(json)) {
                            ArrayList data = mGson.fromJson(json, type);
                            return Observable.just(data);
                        } else {
                            return Observable.just(new ArrayList());
                        }

                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public Observable postList(String url, Map params, Object body, final Type type) {
        return mIHttpService.postWithQueryBody(url, params, body)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<? extends ArrayList>>() {
                    @Override
                    public ObservableSource<ArrayList> apply(String json) throws Exception {
                        if (json != null && !TextUtils.isEmpty(json)) {
                            ArrayList data = mGson.fromJson(json, type);
                            return Observable.just(data);
                        } else {
                            return Observable.just(new ArrayList());
                        }

                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> get(final String url, final Map params, final Class<T> cls) {
        return mIHttpService.get(url, params)
                .subscribeOn(Schedulers.newThread())
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null && !StringUtils.isEmpty(json) && json != "null") {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    //获取文件流数据
    public Observable<Object> get(final String url, final Class<Object> cls) {
        return mIHttpService.download(url)
                .subscribeOn(Schedulers.newThread())
                .map(new InputStreamFunc())
                .flatMap(new Function<InputStream, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(InputStream inputStream) throws Exception {

                        byte[] data = ConvertUtils.inputStream2Bytes(inputStream);
                        String dataObject = Base64.encodeToString(data, Base64.DEFAULT);
                        return Observable.just(dataObject, cls);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> getWithBody(String url, Map params, Object body, final Class<T> cls) {
        return mIHttpService.getWithBody(url, params, body)
                .subscribeOn(Schedulers.newThread())
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public Observable getList(String url, Map params, final Type type) {
        return mIHttpService.get(url, params)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<? extends ArrayList>>() {
                    @Override
                    public Observable<ArrayList> apply(String json) throws Exception {
                        if (json != null && !TextUtils.isEmpty(json)) {
                            ArrayList data = mGson.fromJson(json, type);
                            return Observable.just(data);
                        } else {
                            return Observable.just(new ArrayList());
                        }
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);

                        LogUtils.d(TAG, "获取 list 异常 = " + throwable.getMessage());
                    }
                });
    }

    @Override
    public <T> Observable<T> put(String url, Map params, final Class<T> cls) {
        return mIHttpService.put(url, params)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null && json != "null") {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> putWithBody(String url, Object body, final Class<T> cls) {
        return mIHttpService.putWithBody(url, body)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> putWithBodyAndParam(String url, Map params, Object body, final Class<T> cls) {
        return mIHttpService.putWithBodyAndParam(url, params, body)
                .map(new HttpClientImp.CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> delete(String url, final Class<T> cls) {
        return mIHttpService.delete(url)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    @Override
    public <T> Observable<T> delete(String url, Map<String, String> params, final Class<T> cls) {
        return mIHttpService.delete(url, params)
                .map(new CodeHandleFunc())
                .flatMap(new Function<String, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(String json) throws Exception {
                        if (json != null) {
                            T data = mGson.fromJson(json, cls);
                            return Observable.just(data);
                        }
                        return Observable.just(cls.newInstance());
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errorHandle(throwable);
                    }
                });
    }

    //code处理
    private static class CodeHandleFunc implements Function<ResponseBody, String> {
        @Override
        public String apply(ResponseBody responseBody) throws Exception {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            String message = jsonObject.optString("message");
//            Log.e(TAG, "message = " + message + "  responseBody = " + responseBody + " jsonObject = " + jsonObject);
            //其他
            if (jsonObject.optInt("code") == 300) {
                //token 过期
                //去登陆
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ARouter.getInstance().build("/account/login")
                                .navigation();
                    }
                }, 500);
                throw new ApiException(jsonObject.optInt("code"), "登录过期");
            }
            if (jsonObject.optInt("code") == 1111) {
                //token 过期获取新动向的token
                throw new ApiException(jsonObject.optInt("code"), message);
            }

            if (jsonObject.optInt("code") == 101) {
                //未知错误
                throw new ApiException(jsonObject.optInt("code"), message);
            }

            //失败
            if (jsonObject.optInt("code") == -1) {
                //未知错误
                throw new ApiException(jsonObject.optInt("code"), message);
            }
            //成功
            if (jsonObject.optInt("code") == 200) {
                    /*//data不是json格式
                    if (!isValidJson(jsonObject.optString("data"))) {
                        Log.e(TAG, "apply: 不是json格式" );
                        return "{\"result\"" + ":\"" + jsonObject.optString("data") + "\"" + "}";
                    } else {
                        //是json格式
                        Log.e(TAG, "apply: 是json格式" );
                        return jsonObject.optString("data");
                    }*/
                return jsonObject.optString("data");
            }
            return new JSONObject().toString();
        }
    }

    //数据流
    private static class InputStreamFunc implements Function<ResponseBody, InputStream> {
        @Override
        public InputStream apply(ResponseBody responseBody) throws Exception {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            String message = jsonObject.optString("message");
            if (jsonObject.optInt("code") == 300) {
                //token 过期
                //去登陆
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ARouter.getInstance().build("/account/login")
                                .navigation();
                    }
                }, 500);
                throw new ApiException(jsonObject.optInt("code"), "登录过期");
            }
            if (jsonObject.optInt("code") == 1111) {
                //token 过期获取新动向的token
                throw new ApiException(jsonObject.optInt("code"), message);
            }

            if (jsonObject.optInt("code") == 101) {
                //未知错误
                throw new ApiException(jsonObject.optInt("code"), message);
            }

            //失败
            if (jsonObject.optInt("code") == -1) {
                //未知错误
                throw new ApiException(jsonObject.optInt("code"), message);
            }
            //成功
            if (jsonObject.optInt("code") == 200) {

                return responseBody.byteStream();
            }
            return null;
        }
    }


}
