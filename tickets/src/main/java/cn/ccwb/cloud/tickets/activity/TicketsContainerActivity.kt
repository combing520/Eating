package cn.ccwb.cloud.tickets.activity

import cn.ccwb.lib_base.activity.BaseActivityPlusWithoutPresenter
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * 门票
 */
@Route(path = RouterPath.PAGE_GUIDE_TICKETS)
class TicketsContainerActivity : BaseActivityPlusWithoutPresenter() {

    /**
     * 跳转的页面
     */
    @JvmField
    @Autowired(name = RouterPath.TAG_PATH_FRAGMENT)
    var mFragmentPath: String? = null

    @JvmField
    @Autowired(name = RouterPath.TAG_ID)
    var mId: String? = null

    @JvmField
    @Autowired(name = RouterPath.TAG_URL)
    var mUrl: String? = null

    @JvmField
    @Autowired(name = RouterPath.TAG_FIT_SYSTEM)
    var isFitSystem: Boolean = true

    override fun addContentView(): ISupportFragment? {
        return ARouter.getInstance()
            .build(mFragmentPath)
            .withString(RouterPath.TAG_ID, mId)
            .withString(RouterPath.TAG_URL, mUrl)
            .navigation() as ISupportFragment
    }

    override fun initView() {
        initStatusBar(isFitSystem)
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    /**
     * 初始化导航头信息
     */
    private fun initStatusBar(isFitSystem: Boolean) {
        ImmersionBar.with(this@TicketsContainerActivity)
            .statusBarDarkFont(true, 0.0f)
            .transparentStatusBar()
            .init()
        mRootViewBinding!!.root.fitsSystemWindows = isFitSystem
    }
}