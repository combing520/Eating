package cn.ccwb.cloud.restaurant.mvvm

import cn.ccwb.cloud.restaurant.entity.ShopInfoDetailEntity
import cn.ccwb.cloud.restaurant.entity.ShopInfoEntity
import cn.ccwb.lib_net.http.HttpClientImp
import cn.ccwb.lib_net.path.HttpUrlPath
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

/**
 * 对应定义的 用户中心接口进行实现
 */
class RestaurantServiceImp : RestaurantService {
    private val mHttpClientImp: HttpClientImp = HttpClientImp.getInstance()

    companion object {
        @Volatile
        private var mTicketsServiceImp: RestaurantServiceImp? = null

        @JvmStatic
        val INSTANCE: RestaurantServiceImp?
            get() {
                if (mTicketsServiceImp == null) {
                    synchronized(RestaurantServiceImp::class.java) {
                        if (mTicketsServiceImp == null) {
                            mTicketsServiceImp =
                                RestaurantServiceImp()
                        }
                    }
                }
                return mTicketsServiceImp
            }
    }

    /**
     * 获取店铺列表信息
     */
    override fun getShopInfoList(keywords: String?, category: Int, distance: Int, order: String, p: Int): Observable<MutableList<ShopInfoEntity>> {
        val params = HashMap<String, Any>()
        params["category"] = category
        params["distance"] = distance
        params["order"] = order
        params["p"] = p
        if(!keywords.isNullOrEmpty()){
            params["keywords"] = keywords
        }
        return mHttpClientImp.getList(HttpUrlPath.SHOP_LIST.path,params, object : TypeToken<List<ShopInfoEntity>>() {}.type)as Observable<MutableList<ShopInfoEntity>>
    }

    /**
     * 获取商铺详情信息
     */
    override fun getShopInfoDetail(id:String): Observable<ShopInfoDetailEntity>? {
        val params = HashMap<String, Any>()
        return mHttpClientImp.get(String.format(HttpUrlPath.SHOP_DETAIL.path,id),params,ShopInfoDetailEntity::class.java)
    }

}