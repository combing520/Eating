package cn.ccwb.cloud.order.mvvm

import cn.ccwb.lib_net.http.HttpClientImp

/**
 * 对应定义的 优惠券接口进行实现
 */
class OrderServiceImp : OrderService {
    private val mHttpClientImp: HttpClientImp = HttpClientImp.getInstance()

    companion object {
        @Volatile
        private var mTicketsServiceImp: OrderServiceImp? = null

        @JvmStatic
        val INSTANCE: OrderServiceImp?
            get() {
                if (mTicketsServiceImp == null) {
                    synchronized(OrderServiceImp::class.java) {
                        if (mTicketsServiceImp == null) {
                            mTicketsServiceImp =
                                OrderServiceImp()
                        }
                    }
                }
                return mTicketsServiceImp
            }
    }
}