package cn.ccwb.lib_net.http

import cn.ccwb.lib_net.interceptor.HeaderInterceptor
import com.blankj.utilcode.util.SPUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIHelper {
    companion object {
        const val TIME_OUT: Long = 15L

        @Volatile
        private var mAPIHelper: APIHelper? = null

        @JvmStatic
        val instance: APIHelper?
            get() {
                if (mAPIHelper == null) {
                    synchronized(APIHelper::class.java) {
                        if (mAPIHelper == null) {
                            mAPIHelper = APIHelper()
                        }
                    }
                }
                return mAPIHelper
            }

        private fun isDebugEnv(): Boolean {
            return SPUtils.getInstance().getBoolean(APIConstants.DOMAIN_TAG, true)
        }

        private fun setDebugEven(isDebug: Boolean) {
            SPUtils.getInstance().put(APIConstants.DOMAIN_TAG, isDebug)
        }

        private fun getBaseUrl(): String {
            return if (isDebugEnv()) APIConstants.BASE_URL_DEBUG else APIConstants.BASE_URL_RELEASE
        }
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor()) //添加head
            .addInterceptor(loggingInterceptor) // 设置日志打印
            .connectTimeout(APIHelper.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(APIHelper.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(APIHelper.TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    fun <T> createService(service: Class<T>?): T {
        return createRetrofit().create(service)
    }
}