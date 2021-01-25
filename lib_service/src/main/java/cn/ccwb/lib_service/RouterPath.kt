package cn.ccwb.lib_service

import java.util.*

/**
 * Author: yyg
 * Date: 2020/4/14 15:09
 * Description:
 */
object RouterPath {

    const val TAG_ID = "tag_id"
    const val TAG_POS = "tag_pos"
    const val TAG_LIST = "tag_list"
    const val TAG_FIT_SYSTEM = "tag_fit_system"
    const val TAG_PATH_FRAGMENT = "fragment_path_tag"
    const val TAG_URL = "tag_url"

    //-----------------首页 begin-----------//
    const val PAGE_MAIN = "/home/main"
    //-----------------首页 end-----------//

    const val ERROR_PAGE = "/error/page"

    //-----------------商品 begin-----------//
    const val PAGE_GUIDE_GOODS = "/goods/guide"
    const val PAGE_GOODS_DISCOUNT = "/goods/discount"
    //-----------------商品 end-----------//

    //-----------------优惠券 bgin-----------//
    const val PAGE_GUIDE_TICKETS = "/tickets/guide"

    //-----------------优惠券 end-----------//


    private val sName2Path =
        HashMap<String, String?>()

    fun matchName2Path(name: String?): String? {
        return if (sName2Path.containsKey(name)) {
            sName2Path[name]
        } else {
            ERROR_PAGE
        }
    }

    init {
        sName2Path["首页"] = PAGE_MAIN
        sName2Path["打折"] = PAGE_GOODS_DISCOUNT

    }
}