package cn.ccwb.lib_net.path

import java.util.ArrayList

/**
 * http 请求 url
 */
object HttpUrlPath {
    @JvmField
    var sWePaths: MutableList<WePath> = ArrayList()

    /**
     * 商品列表
     */
    @JvmField
    val GOODS_LIST = WePath("/eat/goods", false)

    /**
     * 商铺列表
     */
    @JvmField
    val SHOP_LIST = WePath("/eat/store", false)

    /**
     * 商铺详情
     */
    @JvmField
    val SHOP_DETAIL = WePath("/eat/store/%S", false)




    init {
        sWePaths.add(GOODS_LIST)
        sWePaths.add(SHOP_LIST)
    }
}