package cn.ccwb.cloud.restaurant.adapter

import android.widget.TextView
import cn.ccwb.cloud.restaurant.R
import cn.ccwb.cloud.restaurant.entity.ShopInfoEntity
import cn.ccwb.lib_base.utils.GlideUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import per.wsj.library.AndRatingBar

/**
 * 商铺列表信息
 */
class ShopListAdapter(layoutResId: Int, data: MutableList<ShopInfoEntity>?) :
    BaseQuickAdapter<ShopInfoEntity, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: ShopInfoEntity) {
        // 店铺名称
        holder.getView<TextView>(R.id.name_tv).text = item.name
        // 评分
        holder.getView<TextView>(R.id.score_tv).text = item.score.toString() + "分"
        //五角星
        holder.getView<AndRatingBar>(R.id.score_bar).rating = item.score.toFloat()
        // 距离
        holder.getView<TextView>(R.id.distance_tv).text = if (item.distance >= 1000) (String.format(
            "%.2f", item.distance / 1000.0f
        )) + "km" else (item.distance.toString() + "m")
        // 类型
        holder.getView<TextView>(R.id.type_tv).text = item.category_name
        // 优惠信息
        holder.getView<TextView>(R.id.discount_describe_tv).text = item.coupon
        //代金券信息
        holder.getView<TextView>(R.id.discount_info_tv).text = item.voucher
        // 描述信息
        holder.getView<TextView>(R.id.describe_tv).text = item.branch_name

        GlideUtils.loadNetPic(item.logo, context, holder.getView(R.id.shop_img), R.drawable.img_default_news)

    }
}