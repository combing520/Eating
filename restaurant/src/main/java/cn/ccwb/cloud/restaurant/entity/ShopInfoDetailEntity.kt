package cn.ccwb.cloud.restaurant.entity

/**
 * 店铺详情信息
 */
data class ShopInfoDetailEntity(
    val id: Int,
    val merchant_id: Int?,
    val store_id: Int?,
    val type_id: Int?,
    val name: String?,
    val logo: String?,
    val start_time: String?,
    val end_time: String?,
    val score: Int?,
    val score_num: Int?,
    val sales: Int,
    val sales_half_year: Int?,
    val hits: Int?,
    val tag: String?,
    val per_capita: Int?,
    val remind: String?,
    val is_recommend: Int?,
    val open: Int?,
    val status: Int?,
    val create_time: String?,
    val update_time: String?,
    val delete_time: Int?,
    val branch_store: List<BranchStore>?,
    val goods: Goods?
)

data class BranchStore(
    val id: Int,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?
)

data class Goods(
    val voucher :List<Voucher>?,
    val coupon:List<Coupon>
)

data class Voucher(
    val id: Int,
    val name: String?,
    val type:String?,
    val cover:String?,
    val original_price:String?,
    val price:String?
)

data class Coupon(
    val id: Int,
    val name: String?,
    val type:String?,
    val cover:String?,
    val original_price:String?,
    val price:String?
)