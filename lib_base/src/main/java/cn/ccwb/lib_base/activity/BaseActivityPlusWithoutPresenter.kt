package cn.ccwb.lib_base.activity

import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.databinding.LibUiBaseActivityBinding
import cn.ccwb.lib_base.mvp.IPresenter
import cn.ccwb.lib_base.mvp.IView
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog.CustomBuilder
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportActivity

/**
 * Author: yyg
 * Date: 2020/4/20 17:27
 * Description:
 */
abstract class BaseActivityPlusWithoutPresenter : SupportActivity() {
    protected var mRootViewBinding: LibUiBaseActivityBinding? = null
    protected abstract fun addContentView(): ISupportFragment?
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()

    private var tipDialog: QMUITipDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true).init()
        }
        mRootViewBinding = LibUiBaseActivityBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding?.getRoot())
        loadRootFragment(R.id.base_activity_container, addContentView()!!)
        initView()
        initData()
        initListener()
    }

    override fun onDestroy() {
        if (isCenterTipsShowing) {
            hideCenterTips()
        }
        super.onDestroy()
    }

    fun showCenterTips(msg: String, QMUITipDialogBuilderIconType: Int) {
        if (QMUITipDialogBuilderIconType < 0 || QMUITipDialogBuilderIconType > 4) return
        tipDialog = QMUITipDialog.Builder(this)
            .setIconType(QMUITipDialogBuilderIconType)
            .setTipWord(msg)
            .create()
        tipDialog?.show()
    }

    fun showCenterTips(layoutId: Int) {
        tipDialog = CustomBuilder(this)
            .setContent(layoutId)
            .create()
        tipDialog?.show()
    }

    fun showCenterTips(
        msg: String, QMUITipDialogBuilderIconType: Int, autoDismissTime: Int,
        callback: BaseActivity.TipDialogCallback?
    ) {
        showCenterTips(msg, QMUITipDialogBuilderIconType)
        window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
                if (callback != null) {
                    callback.onDismiss()
                }
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

    fun showCenterTips(
        layoutId: Int,
        autoDismissTime: Int,
        callback: BaseActivity.TipDialogCallback?
    ) {
        showCenterTips(layoutId)
        window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
                if (callback != null) {
                    callback.onDismiss()
                }
            }
        }, autoDismissTime.toLong())
    }

    interface TipDialogCallback {
        fun onDismiss()
    }

    fun hideCenterTips() {
        if (tipDialog != null) {
            tipDialog!!.dismiss()
            tipDialog = null
        }
    }

    val isCenterTipsShowing: Boolean
        get() = tipDialog != null && tipDialog!!.isShowing

    fun showToastTop(msg: String?) {
        ToastUtils.setGravity(Gravity.TOP or Gravity.CENTER, 0, 200)
        ToastUtils.showShort(msg)
    }

    fun showToastTop(res: Int) {
        ToastUtils.setGravity(Gravity.TOP or Gravity.CENTER, 0, 200)
        ToastUtils.showShort(res)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            val v = currentFocus //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (KeyboardUtils.isSoftInputVisible(this)) {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}