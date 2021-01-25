package cn.ccwb.lib_net.http;

import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;

/**
 * IHttpClient
 */
public interface IHttpClient {

    <T> Observable<T> get(String url, Map params, Class<T> cls);

    <T> Observable<T> getWithBody(String url, Map params, Object body, Class<T> cls);

    <T> Observable<T> put(String url, Map params, Class<T> cls);

    <T> Observable<T> putWithBody(String url, Object body, Class<T> cls);


    <T> Observable<T> putWithBodyAndParam(String url, Map params, Object body, Class<T> cls);

    <T> Observable<T> delete(String url, Class<T> cls);

    <T> Observable<T> delete(String url, Map<String, String> params, Class<T> cls);


    <T> Observable<T> post(String url, Map params, Class<T> cls);

    <T> Observable<T> postString(String url, Map params, Class<T> cls);

    <T> Observable<T> postWithQueryBody(String url, Map params, Object body, Class<T> cls);

    <T> Observable<T> postWithBody(String url, Object body, Class<T> cls);

    <T> Observable<T> postWithFormBody(String url, Object[] body, Class<T> cls);

    <T> Observable<T> upload(String url, Map<String, String> fileMap, Class<T> cls);

    <T> Observable<T> download(String url, Class<T> cls);


    Observable postList(String url, Object body, Type type);

    Observable postList(String url, Map params, Object body, Type type);

    Observable getList(String url, Map params, Type type);


}
