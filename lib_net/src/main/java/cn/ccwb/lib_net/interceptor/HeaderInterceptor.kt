package cn.ccwb.lib_net.interceptor

import cn.ccwb.lib_net.http.Constants
import cn.ccwb.lib_net.path.PathManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // 添加公告参数
        val builder: HttpUrl.Builder = original.url()
            .newBuilder()
            .removeAllQueryParameters("cw-authorization")
            .setEncodedQueryParameter(Constants.CW_OS, Constants.CW_CLIENT)
            .setEncodedQueryParameter(Constants.CW_VERSION_STR, AppUtils.getAppVersionName())
            .setEncodedQueryParameter(Constants.CW_CLIENT_STR, Constants.CW_CLIENT)
            .setEncodedQueryParameter(Constants.CW_DEVICE_STR, Constants.CW_CLIENT)
            .setEncodedQueryParameter(Constants.CW_OS, Constants.CW_CLIENT)
            .setEncodedQueryParameter(Constants.CW_MACHINE_ID_STR, Constants.CW_MACHINE_ID)
            .setEncodedQueryParameter(Constants.CW_COUNTRY_STR, Constants.CW_COUNTRY)
            .setEncodedQueryParameter(Constants.CW_PROVINCE_STR, Constants.CW_PROVINCE)
            .setEncodedQueryParameter(Constants.CW_CITY_STR, Constants.CW_CITY)
            .setEncodedQueryParameter(Constants.CW_AREA_STR, Constants.CW_AREA)
            .setEncodedQueryParameter(Constants.CW_LONGITUDE_STR, Constants.CW_LONGITUDE.toString())
            .setEncodedQueryParameter(Constants.CW_LATITUDE_STR, Constants.CW_LATITUDE.toString())
            .setEncodedQueryParameter(Constants.CW_IP_STR, Constants.IP)
            .setEncodedQueryParameter(Constants.CW_DEVICEMODEL_STR, Constants.CW_DEVICEMODEL)
            .setEncodedQueryParameter(Constants.CW_VERSION_STR, AppUtils.getAppVersionName())
            .setEncodedQueryParameter(Constants.AD_CODE_STR, Constants.AD_CODE)

        val requestBuilder = original.newBuilder()
            .header("Content-Type", "application/json; charset=UTF-8")
            .url(builder.build())
            .method(original.method(), original.body())

        //根据是否需要token添加token
        if (PathManager.instance!!.isNeedToken(original.url().url().path)) {
            requestBuilder.addHeader(
                "cw-authorization",
                SPUtils.getInstance().getString("token", "")
            )
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}