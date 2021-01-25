package cn.ccwb.lib_base.activity

import android.app.Application
import android.os.Bundle
import android.view.Gravity
import androidx.lifecycle.ViewModelProvider
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.databinding.LibUiBaseActivityBinding
import cn.ccwb.lib_base.mvvm.BaseViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import com.alibaba.android.arouter.launcher.ARouter
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
abstract class BaseActivityWithViewModel<T : BaseViewModel<*>?, M : BaseViewPro?> : SupportActivity() {
    protected var mRootViewBinding: LibUiBaseActivityBinding? = null
    protected abstract fun addContentView(): ISupportFragment?
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    protected var mViewModel: T? = null
    protected var mView: M? = null
    protected abstract fun setViewModel(): Class<T>?
    protected abstract fun setApp(): Application?
    protected abstract fun setView(): M
    private var tipDialog: QMUITipDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true).init()
        }
        mViewModel = ViewModelProvider.AndroidViewModelFactory(setApp()!!).create(setViewModel()!!)
        mView = setView()
        if (mViewModel != null) {
            mViewModel?.attachView(mView)
            mViewModel?.attachLifecycleOwner(this)
        }
        mRootViewBinding = LibUiBaseActivityBinding.inflate(layoutInflater)
        setContentView(mRootViewBinding!!.root)
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

    enum class TipsType(val value: Int) {
        NOTHING(0), LOADING(1), SUCCESS(2), ERROR(3), INFO(4);

    }

    fun showCenterTips(msg: String, type: TipsType) {
        tipDialog = QMUITipDialog.Builder(this)
            .setIconType(type.value)
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

    fun showCenterTips(msg: String, type: TipsType, autoDismissTime: Int) {
        showCenterTips(msg, type)
        window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun showCenterTips(msg: String, type: TipsType, autoDismissTime: Int,
                       callback: TipDialogCallback?) {
        showCenterTips(msg, type)
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
}