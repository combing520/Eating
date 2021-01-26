package cn.ccwb.cloud.usercenter.mvvm

import cn.ccwb.lib_net.http.HttpClientImp

/**
 * 对应定义的 用户中心接口进行实现
 */
class UserCenterServiceImp : UserCenterService {
    private val mHttpClientImp: HttpClientImp = HttpClientImp.getInstance()

    companion object {
        @Volatile
        private var mTicketsServiceImp: UserCenterServiceImp? = null

        @JvmStatic
        val INSTANCE: UserCenterServiceImp?
            get() {
                if (mTicketsServiceImp == null) {
                    synchronized(UserCenterServiceImp::class.java) {
                        if (mTicketsServiceImp == null) {
                            mTicketsServiceImp =
                                UserCenterServiceImp()
                        }
                    }
                }
                return mTicketsServiceImp
            }
    }
}