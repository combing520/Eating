package cn.ccwb.cloud.goods.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import cn.ccwb.cloud.goods.R
import cn.ccwb.cloud.goods.adapter.DiscountGoodsAdapter
import cn.ccwb.cloud.goods.application.GoodsAppContext
import cn.ccwb.cloud.goods.databinding.LibGoodsDiscountToolbarBinding
import cn.ccwb.cloud.goods.databinding.LibGoodsFragmentDiscountBinding
import cn.ccwb.cloud.goods.mvvm.DiscountViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils

/**
 * 优惠套餐 列表
 */
@Route(path = RouterPath.PAGE_GOODS_DISCOUNT)
class DiscountListFragment : BaseFragmentWithViewModel<DiscountViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibGoodsDiscountToolbarBinding
    private lateinit var mView: LibGoodsFragmentDiscountBinding
    private lateinit var mAdapter: DiscountGoodsAdapter

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibGoodsDiscountToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibGoodsFragmentDiscountBinding.inflate(inflater!!)
        return mView.root
    }


    override fun setViewModel(): Class<DiscountViewModel>? {
        return DiscountViewModel::class.java
    }

    override fun setApp(): Application? {
        return GoodsAppContext.INSTANCE?.mApplication
    }

    override fun initView() {
        mAdapter = DiscountGoodsAdapter(R.layout.lib_goods_discount_home_item, null)
        mView.discountGoodsRecycle.adapter = mAdapter
    }

    override fun initData() {
        mViewModel?.getGoodsDiscountComboInfo("coupon", 0, 5000, "default")
        mViewModel?.mDiscountGoodsList?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                LogUtils.e("不为空！！！")
                mView.discountGoodsEmpty.visibility = View.GONE
                mView.discountGoodsRecycle.visibility = View.VISIBLE
                mAdapter.setList(it)
            } else {
                LogUtils.e("为空！！！")
                mView.discountGoodsRecycle.visibility = View.GONE
                mView.discountGoodsEmpty.visibility = View.VISIBLE
            }
        })
    }

    override fun hideOtherView() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
        mView.discountGoodsEmpty.setButton("点击重试") {
            mViewModel?.getGoodsDiscountComboInfo("voucher", 0, 5000, "default")
        }
        mToolBar.address.setOnClickListener {
            ARouter.getInstance().build(RouterPath.PAGE_GUIDE_TICKETS)
                .withString(RouterPath.TAG_PATH_FRAGMENT, RouterPath.matchName2Path("优惠券详情"))
                .withString(RouterPath.TAG_ID, "")
                .withBoolean(RouterPath.TAG_FIT_SYSTEM, true)
                .navigation()
        }
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
}