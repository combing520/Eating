package cn.ccwb.cloud.usercenter.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.usercenter.R
import cn.ccwb.cloud.usercenter.application.UserCenterAppContext
import cn.ccwb.cloud.usercenter.databinding.LibUsercenterFragmentMessageDetailBinding
import cn.ccwb.cloud.usercenter.mvvm.UserCenterViewModel
import cn.ccwb.lib_base.databinding.LibUiBaseFragmentBaseAppBarBinding
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar

/**
 * 系统消息详情
 */
@Route(path = RouterPath.PAGE_USERCENTER_MESSAGE_DETAIL)
class SystemMessageDetailFragment : BaseFragmentWithViewModel<UserCenterViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibUiBaseFragmentBaseAppBarBinding
    private lateinit var mView: LibUsercenterFragmentMessageDetailBinding

    override fun setViewModel(): Class<UserCenterViewModel>? {
        return UserCenterViewModel::class.java
    }

    override fun setApp(): Application? {
        return UserCenterAppContext.INSTANCE?.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibUsercenterFragmentMessageDetailBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibUiBaseFragmentBaseAppBarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        mToolBar.baseFragmentShare.visibility = View.GONE
        mToolBar.baseFragmentTitle.text = "通知详情"
        ImmersionBar.with(this).statusBarColor(R.color.color_gray).statusBarDarkFont(true).init()
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
}