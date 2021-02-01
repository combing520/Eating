package cn.ccwb.cloud.restaurant.mvvm

import cn.ccwb.cloud.restaurant.entity.ShopInfoDetailEntity
import cn.ccwb.cloud.restaurant.entity.ShopInfoEntity
import io.reactivex.Observable

/**
 * 定义所有的 用户中心接口相关
 */
interface RestaurantService {

    /**
     * 获取商铺列表信息
     * @param keywords  关键字
     * @param category  分类id, 0(或者空) 所有分类,1,2,3…
     * @param distance    距离,单位 m, 0(或者空系统默认距离),1(1米),2000(2公里)…
     * @param order     排序条件, default(默认),score(评分高到低),distance(距离近到远),sales(销量高到低),price_high(最贵),price_low(便宜)
     * @param p         分页,第几页
     */
    fun getShopInfoList(keywords: String?, category: Int, distance: Int, order: String, p: Int): Observable<MutableList<ShopInfoEntity>>?

    /**
     * 获取商铺详情信息
     */
    fun getShopInfoDetail(id:String): Observable<ShopInfoDetailEntity>?
}