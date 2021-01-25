package cn.ccwb.lib_base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.databinding.LibUiBaseActivityBinding
import cn.ccwb.lib_base.mvp.IPresenter
import cn.ccwb.lib_base.mvp.IView
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar

/**
 * Author: yyg
 * Date: 2020/4/20 17:27
 * Description:
 */
abstract class BaseActivityPro<T : IPresenter?, M : IView?> : BaseActivity<T, M>() {
    protected var mRootViewBinding: LibUiBaseActivityBinding? = null
    protected var mContentView: View? = null
    protected abstract fun addContentView(layoutInflater: LayoutInflater?): View?
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true).init()
        }
        mRootViewBinding = LibUiBaseActivityBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding!!.root)
        mContentView = addContentView(layoutInflater)
        if (mContentView != null) {
            mRootViewBinding!!.baseActivityContainer.addView(mContentView)
        }
        initView()
        initData()
        initListener()
    }
}