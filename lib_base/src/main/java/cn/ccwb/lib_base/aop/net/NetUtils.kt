package cn.ccwb.lib_base.aop.net
import android.content.Context
import android.net.ConnectivityManager

object NetUtils {

    /**
     * 判断网络连接状态
     *
     * @param context 上下文对象
     * @return 网络是否连接
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager
                .activeNetworkInfo
            if (networkInfo != null) {
                return networkInfo.isAvailable
            }
        }
        return false
    }

}