package cn.ccwb.cloud.goods.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.goods.application.GoodsAppContext
import cn.ccwb.cloud.goods.databinding.LibGoodsDiscountToolbarBinding
import cn.ccwb.cloud.goods.databinding.LibGoodsFragmentDiscountBinding
import cn.ccwb.cloud.goods.mvvm.DiscountViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 打折优惠 列表
 */
@Route(path = RouterPath.PAGE_GOODS_DISCOUNT)
class DiscountListFragment : BaseFragmentWithViewModel<DiscountViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibGoodsDiscountToolbarBinding
    private lateinit var mView: LibGoodsFragmentDiscountBinding

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
    }

    override fun initData() {
    }

    override fun hideOtherView() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
        mToolBar.address.setOnClickListener {
            ARouter.getInstance().build(RouterPath.PAGE_GUIDE_TICKETS)
                .withString(RouterPath.TAG_PATH_FRAGMENT, RouterPath.matchName2Path("优惠券详情"))
                .withString(RouterPath.TAG_ID, "")
                .withBoolean(RouterPath.TAG_FIT_SYSTEM, true)
                .navigation()
        }
    }

    override fun hideLoading() {
    }

    override fun showMsg(s: String) {
    }

    override fun showLoading(s: String) {
    }

}