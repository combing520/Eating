package cn.ccwb.cloud.restaurant.entity

/**
 * 商店信息
 */
data class ShopInfoEntity(
    val id: Int,
    val name: String?,
    val logo: String?,
    val merchant_id: Int,
    val start_time: String?,
    val end_time: String?,
    val score: Int,
    val per_capita: Int,
    val branch_id: Int,
    val eat_store_id: Int,
    val branch_name: String?,
    val distance: Int,
    val voucher: String?,
    val coupon: String?,
    val category_name:String?,
    val open_time_status:Boolean
)

///**
// * 代金券
// */
//data class Voucher(
//    val eat_store_id: Int,
//    val id: Int,
//    val name: String?,
//    val price: String?,
//    val original_price: String?
//)
//
///**
// * 优惠券
// */
//data class Coupon(
//    val eat_store_id: Int,
//    val id: Int,
//    val name: String?,
//    val price: String?,
//    val original_price: String?
//)