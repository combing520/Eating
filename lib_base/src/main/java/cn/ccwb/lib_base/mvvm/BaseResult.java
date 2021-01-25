package cn.ccwb.lib_base.mvvm;

/**
 * BaseResult
 */
public class BaseResult<T> {
    public T data;
    public Code code;
    public String msg;
    public boolean isLoadingMore;

    @Override
    public String toString() {
        return "BaseResult{" +
                "data=" + data.toString() +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", isLoadingMore=" + isLoadingMore +
                '}';
    }

    public enum Code {
        success,
        failed,
        loading,
        loadMore
    }

    //加载状态 可以自定义加载显示的内容
    public static BaseResult loading(String msg) {
        BaseResult baseResult = new BaseResult();
        baseResult.code = Code.loading;
        baseResult.msg = msg;
        return baseResult;
    }

    //加载失败
    public static BaseResult failed(String error) {
        BaseResult baseResult = new BaseResult();
        baseResult.code = Code.failed;
        baseResult.msg = error;
        return baseResult;
    }

    //成功
    public static <T> BaseResult success(T data, String msg) {
        BaseResult baseResult = new BaseResult();
        baseResult.code = Code.success;
        baseResult.data = data;
        baseResult.msg = msg;
        return baseResult;
    }

    //成功
    public static <T> BaseResult success(T data, boolean loadMore) {
        BaseResult baseResult = new BaseResult();
        baseResult.code = Code.loadMore;
        baseResult.data = data;
        baseResult.isLoadingMore = loadMore;
        return baseResult;
    }
}
