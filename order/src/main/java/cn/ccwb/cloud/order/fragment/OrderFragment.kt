package cn.ccwb.cloud.order.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.order.R
import cn.ccwb.cloud.order.appliction.OrderAppContext
import cn.ccwb.cloud.order.databinding.LibOrderFragmentHomeBinding
import cn.ccwb.cloud.order.databinding.LibOrderToolbarBinding
import cn.ccwb.cloud.order.mvvm.OrderViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar

/**
 * 订单列表
 */
@Route(path = RouterPath.PAGE_ORDER_LIST)
class OrderFragment : BaseFragmentWithViewModel<OrderViewModel>(), BaseViewPro {

    private lateinit var mView: LibOrderFragmentHomeBinding
    private lateinit var mToolBar: LibOrderToolbarBinding
    override fun setViewModel(): Class<OrderViewModel>? {
        return OrderViewModel::class.java
    }

    override fun setApp(): Application? {
        return OrderAppContext.INSTANCE?.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibOrderFragmentHomeBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibOrderToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        mToolBar.userTitle.text = "我的订单"

        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.colorWhite).init()
    }

    override fun initData() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
    }

    override fun hideOtherView() {
    }

    override fun showMsg(s: String) {
    }

    override fun showLoading(s: String) {
    }

    override fun hideLoading() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ImmersionBar.destroy(this)
    }
}