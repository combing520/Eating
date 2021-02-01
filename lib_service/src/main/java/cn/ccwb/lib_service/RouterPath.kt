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
    const val PAGE_TICKET_NEAR = "/tickets/near"
    const val PAGE_TICKET_DETAIL = "/tickets/detail"

    //-----------------优惠券 end-----------//

    //-----------------用户中心 begin-----------//
    const val PAGE_GUIDE_USERCENTER = "/usercenter/guide"
    const val PAGE_USERCENTER_HOME = "/usercenter/home"
    const val PAGE_USERCENTER_MESSAGE = "/usercenter/message"
    const val PAGE_USERCENTER_MESSAGE_DETAIL = "/usercenter/message/detail"
    const val PAGE_USERCENTER_FOOT_PRINT = "/usercenter/foot_print"
    const val PAGE_USERCENTER_COLLECTION = "/usercenter/collection"
    //-----------------用户中心 end-----------//

    //-----------------订单 begin-----------//
    const val PAGE_GUIDE_ORDER = "/order/guide"
    const val PAGE_ORDER_LIST = "/order/list"
    //-----------------订单 end-----------//

    //-----------------餐厅 begin-----------//
    const val PAGE_GUIDE_RESTAURANT = "/restaurant/guide"
    const val PAGE_RESTAURANT_RANK_LIST = "/restaurant/list_rank"
    const val PAGE_RESTAURANT_RANK_DETAIL = "/restaurant/detail"

    //-----------------餐厅 end-----------//


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
        sName2Path["优惠套餐"] = PAGE_GOODS_DISCOUNT
        sName2Path["附近好券"] = PAGE_TICKET_NEAR
        sName2Path["优惠券详情"] = PAGE_TICKET_DETAIL
        sName2Path["用户中心"] = PAGE_USERCENTER_HOME
        sName2Path["系统通知"] = PAGE_USERCENTER_MESSAGE
        sName2Path["通知详情"] = PAGE_USERCENTER_MESSAGE_DETAIL
        sName2Path["我的足迹"] = PAGE_USERCENTER_FOOT_PRINT
        sName2Path["我的收藏"] = PAGE_USERCENTER_COLLECTION
        sName2Path["我的订单"] = PAGE_ORDER_LIST
        sName2Path["餐厅排行"] = PAGE_RESTAURANT_RANK_LIST
        sName2Path["餐厅详情"] = PAGE_RESTAURANT_RANK_DETAIL

    }
}