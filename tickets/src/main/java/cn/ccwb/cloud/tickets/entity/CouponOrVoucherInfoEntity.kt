package cn.ccwb.cloud.tickets.entity

/**
 * voucher 代金券/coupon 套餐 信息
 */
data class CouponOrVoucherInfoEntity(
    val id: Int,
    val eat_store_id: Int,
    val type: String?,
    val name: String?,
    val cover: String?,
    val original_price: String?,
    val price: String?,
    val holiday_usable: Int,
    val buy_limited: Int,
    val single_limited: Int,
    val half_year_sales: Int,
    val store: Store?
)

/**
 * 商铺信息
 */
data class Store(
    val id: Int,
    val name: String?,
    val type_id: Int,
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
    val category_name: String?
)