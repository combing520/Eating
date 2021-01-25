package cn.ccwb.cloud.goods.mvvm

import cn.ccwb.lib_net.http.HttpClientImp

class DiscountServiceImp : DiscountService {
    private val mHttpClientImp: HttpClientImp = HttpClientImp.getInstance()

    companion object {
        @Volatile
        private var mDiscountServiceImp: DiscountServiceImp? = null

        @JvmStatic
        val INSTANCE: DiscountServiceImp?
            get() {
                if (mDiscountServiceImp == null) {
                    synchronized(DiscountServiceImp::class.java) {
                        if (mDiscountServiceImp == null) {
                            mDiscountServiceImp =
                                DiscountServiceImp()
                        }
                    }
                }
                return mDiscountServiceImp
            }
    }
}