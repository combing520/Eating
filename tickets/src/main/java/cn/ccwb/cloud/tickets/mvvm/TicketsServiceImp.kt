package cn.ccwb.cloud.tickets.mvvm

import cn.ccwb.lib_net.http.HttpClientImp

/**
 * 对应定义的 门票接口进行实现
 */
class TicketsServiceImp : TicketsService {
    private val mHttpClientImp: HttpClientImp = HttpClientImp.getInstance()

    companion object {
        @Volatile
        private var mTicketsServiceImp: TicketsServiceImp? = null

        @JvmStatic
        val INSTANCE: TicketsServiceImp?
            get() {
                if (mTicketsServiceImp == null) {
                    synchronized(TicketsServiceImp::class.java) {
                        if (mTicketsServiceImp == null) {
                            mTicketsServiceImp =
                                TicketsServiceImp()
                        }
                    }
                }
                return mTicketsServiceImp
            }
    }
}