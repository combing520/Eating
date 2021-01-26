package cn.ccwb.cloud.usercenter.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.usercenter.R
import cn.ccwb.cloud.usercenter.application.UserCenterAppContext
import cn.ccwb.cloud.usercenter.databinding.LibUsercenterFragmentHomeBinding
import cn.ccwb.cloud.usercenter.databinding.LibUsercenterHomeToolbarBinding
import cn.ccwb.cloud.usercenter.mvvm.UserCenterViewModel
import cn.ccwb.lib_base.aop.click.AopOnclick
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar

/**
 * 用户中心
 */
@Route(path = RouterPath.PAGE_USERCENTER_HOME)
class UserCenterFragment : BaseFragmentWithViewModel<UserCenterViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibUsercenterHomeToolbarBinding
    private lateinit var mView: LibUsercenterFragmentHomeBinding

    override fun setViewModel(): Class<UserCenterViewModel>? {
        return UserCenterViewModel::class.java
    }

    override fun setApp(): Application? {
        return UserCenterAppContext.INSTANCE?.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibUsercenterFragmentHomeBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibUsercenterHomeToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarColor(R.color.colorWhite).statusBarDarkFont(true).init()
    }

    override fun initData() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
        mToolBar.userMessage.setOnClickListener {
            goToPage(MenuType.TYPE_USER, "系统通知", "", true)
        }
        mView.footRelate.setOnClickListener {
            goToPage(MenuType.TYPE_USER, "我的足迹", "", true)

        }
        mView.collectionRelate.setOnClickListener {
            goToPage(MenuType.TYPE_USER, "我的收藏", "", true)
        }
        mView.moreBtn.setOnClickListener {
            goToPage(MenuType.TYPE_ORDER, "我的订单", "", true)
        }
    }

    @AopOnclick
    private fun goToPage(enumType: MenuType, pathName: String, id: String, fitSystem: Boolean) {
        if (RouterPath.matchName2Path(pathName).equals(RouterPath.ERROR_PAGE)) {
            ToastUtils.showShort("该功能正在开发中...")
        } else {
            //其他的
            val route = ARouter.getInstance()
            var postCard: Postcard? = null
            when (enumType) {
                MenuType.TYPE_USER -> {
                    postCard = route.build(RouterPath.PAGE_GUIDE_USERCENTER)
                }
                MenuType.TYPE_ORDER -> {
                    postCard = route.build(RouterPath.PAGE_GUIDE_ORDER)
                }
            }
            postCard?.withString(RouterPath.TAG_PATH_FRAGMENT, RouterPath.matchName2Path(pathName))
                ?.withString(RouterPath.TAG_ID, id)
                ?.withBoolean(RouterPath.TAG_FIT_SYSTEM, fitSystem)
                ?.navigation()
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


enum class MenuType {
    TYPE_USER,
    TYPE_ORDER
}