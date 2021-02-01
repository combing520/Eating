package cn.ccwb.cloud.goods.adapter

import android.graphics.Paint
import android.widget.TextView
import cn.ccwb.cloud.goods.R
import cn.ccwb.cloud.goods.entity.CouponOrVoucherInfoEntity
import cn.ccwb.lib_base.utils.GlideUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 打折优惠商品列表
 */
class DiscountGoodsAdapter(layoutResId: Int, data: MutableList<CouponOrVoucherInfoEntity>?) :
    BaseQuickAdapter<CouponOrVoucherInfoEntity, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: CouponOrVoucherInfoEntity) {
        // 商店名称
        holder.getView<TextView>(R.id.goods_discount_store_name_tv).text = item.store?.name
        // 距离
        holder.getView<TextView>(R.id.goods_discount_store_distance_tv).text =
            item.store?.distance?.toString()
        // 商品名称
        holder.getView<TextView>(R.id.goods_discount_goods_name_tv).text = item.store?.branch_name
        // 半年销量
        holder.getView<TextView>(R.id.goods_discount_sale_cnt_tv).text =
            item.half_year_sales.toString()
        // 分类名称
        holder.getView<TextView>(R.id.goods_discount_category_name_tv).text =
            item.store?.category_name
        // 使用时间
        if (item.holiday_usable == 1) {
            holder.getView<TextView>(R.id.goods_discount_time_tv).text = "节假日通用"
        } else {
            holder.getView<TextView>(R.id.goods_discount_time_tv).text = "节假日不可用"
        }
        // 购买价格
        holder.getView<TextView>(R.id.goods_discount_cast_tv).text = item.price
        // 原价
        holder.getView<TextView>(R.id.goods_discount_price_tv).apply {
            text = item.original_price
            // 抗锯齿
            paint.isAntiAlias = true
            // 中间划线
            paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        GlideUtils.loadNetPic(
            item.store?.logo,
            context,
            holder.getView(R.id.goods_discount_goods_pic_img),
            R.drawable.img_default_news
        )
    }
}