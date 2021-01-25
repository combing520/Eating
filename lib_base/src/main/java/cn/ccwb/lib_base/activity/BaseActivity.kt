package cn.ccwb.lib_base.activity

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.mvp.IPresenter
import cn.ccwb.lib_base.mvp.IView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

/**
 * BaseActivity
 */
abstract class BaseActivity<T : IPresenter?, M : IView?> : AppCompatActivity() {
    private var tipDialog: QMUITipDialog? = null
    protected var mPresenter: T? = null
    protected var mView: M? = null
    protected fun setPresenter(): T? {
        return null
    }

    protected fun setView(): M? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true).init()
        }
        ARouter.getInstance().inject(this)
        mPresenter = setPresenter()
        mView = setView()
        if (mPresenter != null) {
            mPresenter?.attachView(mView!!)
        }
    }



    private fun showCenterTips(msg: String, QMUITipDialogBuilderIconType: Int) {
        if (QMUITipDialogBuilderIconType < 0 || QMUITipDialogBuilderIconType > 4) return
        tipDialog = QMUITipDialog.Builder(this)
            .setIconType(QMUITipDialogBuilderIconType)
            .setTipWord(msg)
            .create()
        tipDialog?.show()
    }

    private fun showCenterTips(layoutId: Int) {
        tipDialog = QMUITipDialog.CustomBuilder(this)
            .setContent(layoutId)
            .create()
        tipDialog?.show()
    }

    fun showCenterTips(msg: String, QMUITipDialogBuilderIconType: Int, autoDismissTime: Int,
                       callback: TipDialogCallback?) {
        showCenterTips(msg, QMUITipDialogBuilderIconType)
        window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
                callback?.onDismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun showCenterTips(layoutId: Int, autoDismissTime: Int) {
        showCenterTips(layoutId)
        window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun showCenterTips(layoutId: Int, autoDismissTime: Int, callback: TipDialogCallback?) {
        showCenterTips(layoutId)
        window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
                callback?.onDismiss()
            }
        }, autoDismissTime.toLong())
    }

    interface TipDialogCallback {
        fun onDismiss()
    }

    private fun hideCenterTips() {
        if (tipDialog != null) {
            tipDialog!!.dismiss()
            tipDialog = null
        }
    }

    private val isCenterTipsShowing: Boolean
        get() = tipDialog != null && tipDialog!!.isShowing

    fun showToastTop(msg: String?) {
        ToastUtils.setGravity(Gravity.TOP or Gravity.CENTER, 0, 200)
        ToastUtils.showShort(msg)
    }

    fun showToastTop(res: Int) {
        ToastUtils.setGravity(Gravity.TOP or Gravity.CENTER, 0, 200)
        ToastUtils.showShort(res)
    }

    fun showToastCenter(msg: String) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(msg)
    }

    fun showToastCenter(res: Int) {
        ToastUtils.setGravity(Gravity.CENTER,0,0)
        ToastUtils.showShort(res)
    }

    override fun onDestroy() {
        if (mPresenter != null) {
            mPresenter?.detachView()
        }
        if (isCenterTipsShowing) {
            hideCenterTips()
        }
        super.onDestroy()
    }
}
