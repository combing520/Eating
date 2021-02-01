package cn.ccwb.cloud.restaurant.fragment

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import cn.ccwb.cloud.restaurant.R
import cn.ccwb.cloud.restaurant.application.RestaurantAppContext
import cn.ccwb.cloud.restaurant.databinding.LibRestaurantDetailToolbarBinding
import cn.ccwb.cloud.restaurant.databinding.LibRestaurantFragmentDetailBinding
import cn.ccwb.cloud.restaurant.mvvm.RestaurantViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_base.utils.GlideUtils
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar

/**
 * 商品详情
 */
@Route(path = RouterPath.PAGE_RESTAURANT_RANK_DETAIL)
class RestaurantDetailFragment : BaseFragmentWithViewModel<RestaurantViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibRestaurantDetailToolbarBinding
    private lateinit var mView: LibRestaurantFragmentDetailBinding

    @JvmField
    @Autowired(name = RouterPath.TAG_ID)
    var mId: String? = null


    override fun setViewModel(): Class<RestaurantViewModel>? {
        return RestaurantViewModel::class.java
    }

    override fun setApp(): Application? {
        return RestaurantAppContext.INSTANCE!!.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibRestaurantFragmentDetailBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibRestaurantDetailToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarDarkFont(true)
            .statusBarColor(R.color.qmui_config_color_background).init()
    }

    override fun initData() {
        LogUtils.e("店铺详情 id = $mId")
        mViewModel?.mShopInfoDetailEntity?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                mView.shopTitleTv.text = it.name
                mView.rateTv.text = it.score?.toString() + "分"
                mView.rateBar.rating = it.score?.toFloat()!!
                mView.castCntTv.text = "￥" + it.per_capita?.toString() + "/人均"
                GlideUtils.loadNetPic(
                    it.logo,
                    _mActivity,
                    mView.shopImg,
                    R.drawable.img_default_news
                )
                // 店铺禁用, 0 正常,1被禁
                mView.shopStatueTv.text = if (it.status == 0) "正常" else "被禁"
                mView.shopTimeTv.text = it.start_time + "-" + it.end_time

            }
        })
        mViewModel?.getShopInfoDetail(mId.toString())
    }

    private fun makeCall(phoneNumber: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL//dial是拨号的意思
        intent.data = Uri.parse("tel:${phoneNumber}")//这个tel不能改，后面的数字可以随便改
        startActivity(intent)
    }

    override fun initListener() {
        mToolBar.ticketDetailBack.setOnClickListener {
            _mActivity.finish()
        }
        mView.callTv.setOnClickListener {
            makeCall("18213989987")
        }
    }

    override fun hideOtherView() {
    }

    override fun showMsg(s: String) {
        showToastTop(s)
    }

    override fun showLoading(s: String) {
        showCenterTips(s, TipsType.LOADING)
    }

    override fun hideLoading() {
        hideCenterTips()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ImmersionBar.destroy(this)
    }
}