package cn.ccwb.lib_base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.databinding.LibUiBaseFragmentBinding
import cn.ccwb.lib_base.mvp.IView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog.CustomBuilder
import me.yokeyword.fragmentation.SupportFragment

/**
 * BaseFragmentPlus
 */
abstract class BaseFragmentPlus : SupportFragment(), IView, SimpleImmersionOwner {

    protected var mRootViewBinding: LibUiBaseFragmentBinding? = null

    private var mTipDialog: QMUITipDialog? = null

    protected var mActivity: Activity? = null

    protected var mBaseAppbar: View? = null

    protected var mBaseContent: View? = null

    protected abstract fun addContentView(inflater: LayoutInflater?): View?

    protected abstract fun initView()

    protected abstract fun initToolBar(inflater: LayoutInflater?): View?

    protected abstract fun initData()

    protected abstract fun initListener()

    override fun initImmersionBar() {
        ImmersionBar.with(this).init()
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.0f).init()
        }
    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = requireActivity()
        ARouter.getInstance().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootViewBinding = LibUiBaseFragmentBinding.inflate(inflater, container, false)
        mBaseContent = addContentView(inflater)
        mBaseAppbar = initToolBar(inflater)

        if (mBaseContent != null) {
            mRootViewBinding!!.baseFragmentContentContainer.addView(mBaseContent)
        }
        if (mBaseAppbar != null) {
            mRootViewBinding!!.baseFragmentAppbarContainer.addView(mBaseAppbar)
        } else {
            mRootViewBinding!!.baseFragmentAppbarContainer.visibility = View.GONE
        }
        return mRootViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        initListener()
    }


    enum class TipsType(val value: Int) {
        NOTHING(0), LOADING(1), SUCCESS(2), ERROR(3), INFO(4);
    }


    open fun showCenterTips(msg: String, type: TipsType) {
        mTipDialog = QMUITipDialog.Builder(context)
            .setIconType(type.value)
            .setTipWord(msg)
            .create()
        if (mTipDialog!!.isShowing) {
            mTipDialog?.hide()
        }
        mTipDialog?.show()
    }

    open fun showCenterTips(layoutId: Int) {
        mTipDialog = CustomBuilder(context)
            .setContent(layoutId)
            .create()
        if (mTipDialog!!.isShowing) {
            mTipDialog?.dismiss()
        }
        mTipDialog?.show()
    }

    open fun showCenterTips(msg: String, type: TipsType, autoDismissTime: Int) {
        showCenterTips(msg, type)
        activity!!.window.decorView.postDelayed({
            if (mTipDialog != null) {
                mTipDialog?.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    open fun showCenterTips(
        msg: String, type: TipsType, autoDismissTime: Int,
        callback: TipDialogCallback?
    ) {
        showCenterTips(msg, type)
        activity!!.window.decorView.postDelayed({
            if (mTipDialog != null) {
                mTipDialog?.dismiss()
                callback?.onDismiss()
            }
        }, autoDismissTime.toLong())
    }

    open fun showCenterTips(layoutId: Int, autoDismissTime: Int) {
        showCenterTips(layoutId)
        activity!!.window.decorView.postDelayed({
            if (mTipDialog != null) {
                mTipDialog?.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    open fun showCenterTips(
        layoutId: Int,
        autoDismissTime: Int,
        callback: TipDialogCallback?
    ) {
        showCenterTips(layoutId)
        activity!!.window.decorView.postDelayed({
            if (mTipDialog != null) {
                mTipDialog?.dismiss()
                callback?.onDismiss()
            }
        }, autoDismissTime.toLong())
    }

    interface TipDialogCallback {
        fun onDismiss()
    }

    open fun hideCenterTips() {
        if (mTipDialog != null) {
            mTipDialog?.dismiss()
        }
    }

    open fun isCenterTipsShowing(): Boolean {
        return mTipDialog != null && mTipDialog!!.isShowing
    }

    open fun showToastTop(msg: String?) {
        ToastUtils.setGravity(Gravity.TOP or Gravity.CENTER, 0, 200)
        ToastUtils.showShort(msg)
    }

    open fun showToastTop(res: Int) {
        ToastUtils.setGravity(Gravity.TOP or Gravity.CENTER, 0, 200)
        ToastUtils.showShort(res)
    }


    override fun onDetach() {
        super.onDetach()
    }
}