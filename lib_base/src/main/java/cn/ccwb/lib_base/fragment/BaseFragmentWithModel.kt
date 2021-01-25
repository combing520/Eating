package cn.ccwb.lib_base.fragment

import android.app.Activity
import android.content.Context
import android.view.Gravity
import androidx.fragment.app.Fragment
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.mvp.IPresenter
import cn.ccwb.lib_base.mvp.IView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog.CustomBuilder

/**
 * BaseFragmentWithModel
 */
abstract class BaseFragmentWithModel<T : IPresenter> : Fragment(), IView, SimpleImmersionOwner {

    private var mTipDialog: QMUITipDialog? = null

    protected var mActivity: Activity? = null
    protected var mPresenter: T? = null

    protected abstract fun setPresenter(): T?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = requireActivity()
        mPresenter = setPresenter()
        if (mPresenter != null) {
            mPresenter!!.attachView(this)
        }
        ARouter.getInstance().inject(this)
    }


    open fun showCenterTips(
        msg: String,
        type: TipsType
    ) {
        mTipDialog = QMUITipDialog.Builder(context)
            .setIconType(type.value)
            .setTipWord(msg)
            .create()
        if (mTipDialog!!.isShowing) {
            mTipDialog!!.hide()
        }
        mTipDialog?.show()
    }

    open fun showCenterTips(layoutId: Int) {
        mTipDialog = CustomBuilder(context)
            .setContent(layoutId)
            .create()
        if (mTipDialog!!.isShowing) {
            mTipDialog!!.hide()
        }
        mTipDialog?.show()
    }

    open fun showCenterTips(
        msg: String,
        type: TipsType,
        autoDismissTime: Int
    ) {
        showCenterTips(msg, type)
        activity!!.window.decorView.postDelayed({
            if (mTipDialog != null) {
                mTipDialog?.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    open fun showCenterTips(
        msg: String,
        type: TipsType,
        autoDismissTime: Int,
        callback: BaseFragmentPlus.TipDialogCallback?
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
        callback: BaseFragmentPlus.TipDialogCallback?
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

    override fun initImmersionBar() {
        ImmersionBar.with(this).init()
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.0f).init()
        }
    }


    enum class TipsType(val value: Int) {
        NOTHING(0), LOADING(1), SUCCESS(2), ERROR(3), INFO(4);
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.detachView()
            mPresenter = null
        }
    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }
}