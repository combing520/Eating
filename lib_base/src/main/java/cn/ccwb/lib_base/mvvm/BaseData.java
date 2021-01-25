package cn.ccwb.lib_base.mvvm;

/**
 * BaseData
 */
public class BaseData<T extends Object> {
    public T data;
    public Code code;
    public String msg;
    public boolean isLoadingMore;


    public enum Code {
        success,
        failed,
        loading,
        loadMore
    }

    //加载状态 可以自定义加载显示的内容
    public static BaseData loading(String msg) {
        BaseData baseData = new BaseData();
        baseData.code = Code.loading;
        baseData.msg = msg;
        return baseData;
    }

    //加载失败  自定义失内容
    public static BaseData failed(String error) {
        BaseData baseData = new BaseData();
        baseData.code = Code.failed;
        baseData.msg = error;
        return baseData;
    }

    //成功
    public static <T> BaseData<T> success(T data, String msg) {
        BaseData baseData = new BaseData();
        baseData.code = Code.success;
        baseData.data = data;
        baseData.msg = msg;
        return baseData;
    }

    //成功
    public static <T> BaseData<T> success(T data, boolean loadMore) {
        BaseData baseData = new BaseData();
        baseData.code = Code.loadMore;
        baseData.data = data;
        baseData.isLoadingMore = loadMore;
        return baseData;
    }
}
