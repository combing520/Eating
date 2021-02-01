package cn.ccwb.cloud.goods.mvvm

import cn.ccwb.cloud.goods.entity.CouponOrVoucherInfoEntity
import cn.ccwb.lib_net.http.HttpClientImp
import cn.ccwb.lib_net.path.HttpUrlPath
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

/**
 *  优惠打折的 实现
 */
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

    /**
     * 获取优惠打折信息
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