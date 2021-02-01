package cn.ccwb.cloud.goods.mvvm

import cn.ccwb.cloud.goods.entity.CouponOrVoucherInfoEntity
import io.reactivex.Observable

/**
 * 优惠打折
 */
interface DiscountService {

    /**
     * 获取打折信息(商品)
     * @param type 类型, voucher 代金券/coupon 套餐
     * @param category 分类id, 0(或者空) 所有分类,1,2,3…
     * @param distance 距离,单位 m, 0(或者空系统默认距离),1(1米),2000(2公里)……
     * @param 排序条件, default(默认),score(评分高到低),distance(距离近到远),sales(销量高到低),price_high(最贵),price_low(便宜)
     */
    fun getGoodsDiscountComboInfo(type: String, category: Int,distance:Int,order:String): Observable<MutableList<CouponOrVoucherInfoEntity>>
}