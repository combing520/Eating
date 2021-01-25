package cn.ccwb.lib_net.interceptor

import com.blankj.utilcode.util.ToastUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandleInterceptor : Interceptor{
    val TAG = ErrorHandleInterceptor::class.java.simpleName

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val response = chain.proceed(chain.request())
        ToastUtils.showLong(convertStatusCode(response.code()))
        return response
    }

    private fun convertStatusCode(code: Int): String? {
        val msg: String
        msg = if (code == 500) {
            "服务器发生错误"
        } else if (code == 404) {
            "请求地址不存在"
        } else if (code == 403) {
            "请求被服务器拒绝"
        } else if (code == 401) {
            "登陆过期或未登录"
        } else {
            "位置错误"
        }
        return msg
    }

}