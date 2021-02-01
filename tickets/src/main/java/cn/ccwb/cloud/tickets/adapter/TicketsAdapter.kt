package cn.ccwb.cloud.tickets.adapter

import android.widget.TextView
import cn.ccwb.cloud.tickets.R
import cn.ccwb.cloud.tickets.entity.CouponOrVoucherInfoEntity
import cn.ccwb.lib_base.utils.GlideUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.text.DecimalFormat

/**
 * 优惠券信息
 */
class TicketsAdapter(layoutResId: Int, data: MutableList<CouponOrVoucherInfoEntity>?) :
    BaseQuickAdapter<CouponOrVoucherInfoEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: CouponOrVoucherInfoEntity) {

        // 店铺名称
        holder.getView<TextView>(R.id.store_name_tv).text = item.store?.branch_name
        //店铺logo
        GlideUtils.loadNetPic(
            item.store?.logo,
            context,
            holder.getView(R.id.store_name_img),
            R.drawable.img_default_menu
        )
        // 评分
        holder.getView<TextView>(R.id.store_mark_tv).text = item.store?.score?.toString() + "分"
        //人均消费
        holder.getView<TextView>(R.id.store_cast_tv).text =
            "￥" + item.store?.per_capita?.toString() + "人均"
        // 距离
        holder.getView<TextView>(R.id.store_distance_tv).text =
            if (item.store!!.distance >= 1000) (String.format(
                "%.2f",
                item.store.distance / 1000.0f
            )) + "km" else (item.store.distance.toString() + "m")

        // 代金券
        holder.getView<TextView>(R.id.voucher_number_tv).text =
            item.original_price.toString() + "元代金券"
        // 实际花费
        holder.getView<TextView>(R.id.money).text = "￥" + item.price?.toString()

    }
}