package cn.ccwb.lib_net.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * IHttpService
 */
public interface IHttpService {
    @Streaming
    @GET
    Observable<ResponseBody> get(@Url String url);

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params);

    @GET
    Observable<ResponseBody> getWithBody(@Url String url, @QueryMap Map<String, Object> params, @Body Object body);

    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, Object> params);

    @POST
    Observable<ResponseBody> postWithQueryBody(@Url String url, @QueryMap Map<String, Object> queryParams, @Body Object body);

    @POST
    Observable<ResponseBody> postWithBody(@Url String url, @Body Object jsonBody);

    @POST
    Observable<ResponseBody> postWithFormBody(@Url String url, @Body Object jsonBody);

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> params);

    @PUT
    Observable<ResponseBody> putWithBody(@Url String url, @Body Object object);

    @PUT
    Observable<ResponseBody> putWithBodyAndParam(@Url String url, @QueryMap Map<String, String> params, @Body Object object);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @DELETE
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> params);

    @POST
    @Multipart
    Observable<ResponseBody> upload(@Url String url, @PartMap Map<String, RequestBody> files);


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);


}
