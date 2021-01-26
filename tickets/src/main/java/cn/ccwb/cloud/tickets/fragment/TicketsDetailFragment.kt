package cn.ccwb.cloud.tickets.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.tickets.R
import cn.ccwb.cloud.tickets.appliction.TicketsAppContext
import cn.ccwb.cloud.tickets.databinding.LibTicketsDetailToolbarBinding
import cn.ccwb.cloud.tickets.databinding.LibTicketsFragmentDetailBinding
import cn.ccwb.cloud.tickets.mvvm.TicketsViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar

/**
 * 优惠券详情
 */
@Route(path = RouterPath.PAGE_TICKET_DETAIL)
class TicketsDetailFragment : BaseFragmentWithViewModel<TicketsViewModel>(), BaseViewPro {

    @JvmField
    @Autowired(name = RouterPath.TAG_ID)
    var mId: String? = null

    private lateinit var mToolBar: LibTicketsDetailToolbarBinding
    private lateinit var mView: LibTicketsFragmentDetailBinding

    override fun setViewModel(): Class<TicketsViewModel>? {
        return TicketsViewModel::class.java
    }

    override fun setApp(): Application? {
        return TicketsAppContext.INSTANCE?.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibTicketsFragmentDetailBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibTicketsDetailToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        initStatusBar()
    }

    private fun initStatusBar() {
        ImmersionBar.with(this).statusBarColor(R.color.colorGray).statusBarDarkFont(true).init()
    }

    override fun initData() {
    }

    override fun initListener() {
        mToolBar.ticketDetailBack.setOnClickListener {
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