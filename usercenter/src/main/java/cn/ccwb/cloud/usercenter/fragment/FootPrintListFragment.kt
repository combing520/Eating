package cn.ccwb.cloud.usercenter.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.usercenter.R
import cn.ccwb.cloud.usercenter.application.UserCenterAppContext
import cn.ccwb.cloud.usercenter.databinding.LibUsercenterFootToolbarBinding
import cn.ccwb.cloud.usercenter.databinding.LibUsercenterFragmentFootBinding
import cn.ccwb.cloud.usercenter.mvvm.UserCenterViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar

/**
 * 足迹
 */
@Route(path = RouterPath.PAGE_USERCENTER_FOOT_PRINT)
class FootPrintListFragment : BaseFragmentWithViewModel<UserCenterViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibUsercenterFootToolbarBinding
    private lateinit var mView: LibUsercenterFragmentFootBinding

    override fun setViewModel(): Class<UserCenterViewModel>? {
        return UserCenterViewModel::class.java
    }

    override fun setApp(): Application? {
        return UserCenterAppContext.INSTANCE?.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibUsercenterFragmentFootBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibUsercenterFootToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        mToolBar.userTitle.text = "我的足迹"
        ImmersionBar.with(this).statusBarColor(R.color.colorWhite).statusBarDarkFont(true).init()
    }

    override fun initData() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
        mToolBar.userEdit.setOnClickListener {
            //TODO: 进行编辑
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