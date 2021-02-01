package cn.ccwb.cloud.tickets.mvvm

import cn.ccwb.cloud.tickets.entity.CouponOrVoucherInfoEntity
import cn.ccwb.lib_net.http.HttpClientImp
import cn.ccwb.lib_net.path.HttpUrlPath
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

/**
 * 对应定义的 优惠券接口进行实现
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

    /**
     * 获取 优惠券和 套餐信息
     */
    override fun getGoodsDiscountComboInfo(type: String, category: Int, distance: Int, order: String): Observable<MutableList<CouponOrVoucherInfoEntity>> {
        val params = HashMap<String, Any>()
        params["type"] = type
        params["category"] = category
        params["distance"] = distance
        params["order"] = order
        return mHttpClientImp.getList(HttpUrlPath.GOODS_LIST.path,params, object : TypeToken<List<CouponOrVoucherInfoEntity>>() {}.type)as Observable<MutableList<CouponOrVoucherInfoEntity>>
    }
}