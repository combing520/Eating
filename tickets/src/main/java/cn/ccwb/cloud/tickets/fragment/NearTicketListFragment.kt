package cn.ccwb.cloud.tickets.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import cn.ccwb.cloud.tickets.R
import cn.ccwb.cloud.tickets.adapter.TicketsAdapter
import cn.ccwb.cloud.tickets.appliction.TicketsAppContext
import cn.ccwb.cloud.tickets.databinding.LibTicketsNearDiscountToolbarBinding
import cn.ccwb.cloud.tickets.databinding.LibTicketsNearFragmentDiscountBinding
import cn.ccwb.cloud.tickets.mvvm.TicketsViewModel
import cn.ccwb.lib_base.aop.click.AopOnclick
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * 附近好券
 */
@Route(path = RouterPath.PAGE_TICKET_NEAR)
class NearTicketListFragment : BaseFragmentWithViewModel<TicketsViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibTicketsNearDiscountToolbarBinding
    private lateinit var mView: LibTicketsNearFragmentDiscountBinding
    private lateinit var mAdapter: TicketsAdapter

    override fun setViewModel(): Class<TicketsViewModel>? {
        return TicketsViewModel::class.java
    }

    override fun setApp(): Application? {
        return TicketsAppContext.INSTANCE?.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibTicketsNearFragmentDiscountBinding.inflate(inflater!!)
        return mView.root

    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibTicketsNearDiscountToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        mAdapter = TicketsAdapter(R.layout.lib_tickets_near_item, null);
        mView.nearRecycle.adapter = mAdapter
        mViewModel?.mDiscountGoodsList?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                LogUtils.e("不为空！！！")
                mView.nearGoodsEmpty.visibility = View.GONE
                mView.nearRecycle.visibility = View.VISIBLE
                mAdapter.setList(it)
            } else {
                LogUtils.e("为空！！！")
                mView.nearRecycle.visibility = View.GONE
                mView.nearGoodsEmpty.visibility = View.VISIBLE
            }
        })
        mViewModel?.getGoodsDiscountComboInfo("voucher", 0, 5000, "default")
    }

    override fun initData() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
        mToolBar.address.setOnClickListener {
            goToPage("优惠券详情", "")
        }

        mView.nearGoodsEmpty.setButton("点击重试") {
            mViewModel?.getGoodsDiscountComboInfo("voucher", 0, 5000, "default")
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

    @AopOnclick
    private fun goToPage(pathName: String, id: String) {
        if (RouterPath.matchName2Path(pathName).equals(RouterPath.ERROR_PAGE)) {
            ToastUtils.showShort("该功能正在开发中...")
        } else {
            ARouter.getInstance().build(RouterPath.PAGE_GUIDE_TICKETS)
                .withString(RouterPath.TAG_PATH_FRAGMENT, RouterPath.matchName2Path(pathName))
                .withString(RouterPath.TAG_ID, id)
                .withBoolean(RouterPath.TAG_FIT_SYSTEM, true)
                .navigation()
        }
    }
}