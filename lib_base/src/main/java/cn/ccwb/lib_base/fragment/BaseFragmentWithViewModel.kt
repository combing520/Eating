package cn.ccwb.lib_base.fragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.ccwb.lib_base.R
import cn.ccwb.lib_base.databinding.LibUiBaseFragmentBinding
import cn.ccwb.lib_base.mvvm.BaseViewModel
import cn.ccwb.lib_base.mvvm.IViewPro
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog.CustomBuilder
import me.yokeyword.fragmentation.SupportFragment

/**
 * 共享viewmodel的基础fragment,持有对基础视图的抽象引用（控制toast /loading 等）
 * 存在问题：A --> B  View: AView-->BView  ;  B-->A 依然还是BView !!!  导致界面loading无法控制
 * 解决方法：使用BaseResult来做通知、或者onHiddenChanged 回到当前界面重新赋值当前fragment的视图引用给viewmodel(ViewPager中不管用)
 *
 * @param <T>
</T> */
abstract class BaseFragmentWithViewModel<T : BaseViewModel<*>?> : SupportFragment(), IViewPro,
    SimpleImmersionOwner {
    @JvmField
    protected var mRootViewBinding: LibUiBaseFragmentBinding? = null

    @JvmField
    protected var mActivity: Activity? = null

    @JvmField
    protected var mViewModel: T? = null
    protected var mBaseAppbar: View? = null
    protected var mBaseContent: View? = null
    private var tipDialog: QMUITipDialog? = null
    protected abstract fun setViewModel(): Class<T>?
    protected abstract fun setApp(): Application?
    protected abstract fun addContentView(inflater: LayoutInflater?): View?
    protected abstract fun initToolBar(inflater: LayoutInflater?): View?
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    protected abstract fun hideOtherView()

    private var isFirstLoad = true
    override fun initImmersionBar() {
        ImmersionBar.with(this).init()
        ImmersionBar.with(this).statusBarColor(R.color.colorWhite).init()
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.0f).init()
        }
    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity
        ARouter.getInstance().inject(this)
        val cls = setViewModel()
        if (cls != null) {
            mViewModel = ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(setApp()!!)
            )[cls]
        }
        if (mViewModel != null) {
            mViewModel?.attachView(this)
        }
    }

    //注意此方法
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //在不隐藏的时候再赋值一遍
        if (mViewModel != null && !hidden) {
            mViewModel?.attachView(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootViewBinding = LibUiBaseFragmentBinding.inflate(inflater, container, false)
        mBaseContent = addContentView(inflater)
        mBaseAppbar = initToolBar(inflater)
        if (mViewModel != null) {
            mViewModel?.attachLifecycleOwner(this)
        }
        if (mBaseContent != null) {
            mRootViewBinding!!.baseFragmentContentContainer.addView(mBaseContent)
        }
        if (mBaseAppbar != null) {
            mRootViewBinding!!.baseFragmentAppbarContainer.addView(mBaseAppbar)
        }
        return mRootViewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initLiveDataListener()
//        initData()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            initData()
            isFirstLoad = false
        }
    }

    private fun initLiveDataListener() {
        if (mViewModel?.msgLiveData != null) {
            mViewModel?.refreshLiveData?.observe(viewLifecycleOwner, Observer { tipsModel ->
                var msg = ""
                var tipsType: TipsType? = null
                when (tipsModel?.mTipsType) {
                    BaseViewModel.BaseTipsModel.LOADING -> {
                        msg = tipsModel.msg
                        tipsType = TipsType.LOADING
                    }
                    BaseViewModel.BaseTipsModel.SUCCESS -> {
                        msg = tipsModel.msg
                        tipsType = TipsType.SUCCESS
                    }
                    BaseViewModel.BaseTipsModel.INFO -> {
                        msg = tipsModel.msg
                        tipsType = TipsType.INFO
                    }
                    BaseViewModel.BaseTipsModel.NOTHING -> tipsType = TipsType.NOTHING
                    BaseViewModel.BaseTipsModel.HIDE -> {
                    }
                }
                if (tipsType != null) {
                    showCenterTips(msg, tipsType)
                } else {
                    hideCenterTips()
                    hideOtherView()
                }
            })
            mViewModel?.msgLiveData?.observe(
                viewLifecycleOwner,
                Observer { msg -> showToastTop(msg) })
        }
    }

    fun showCenterTips(msg: String, type: TipsType) {

        tipDialog = QMUITipDialog.Builder(mActivity)
            .setIconType(type.value)
            .setTipWord(msg)
            .create()
        if (tipDialog!!.isShowing) {
            tipDialog?.hide()
        }

        tipDialog!!.show()
    }

    private fun showCenterTips(layoutId: Int) {
        tipDialog = CustomBuilder(context)
            .setContent(layoutId)
            .create()
        if (tipDialog!!.isShowing) {
            tipDialog?.dismiss()
        }
        activity!!.window.decorView.postDelayed({ tipDialog?.show() }, 200)
    }

    fun showCenterTips(msg: String, type: TipsType, autoDismissTime: Int) {
        showCenterTips(msg, type)
        activity!!.window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun showCenterTips(
        msg: String, type: TipsType, autoDismissTime: Int,
        callback: TipDialogCallback?
    ) {
        showCenterTips(msg, type)
        activity!!.window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
                callback?.onDismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun showCenterTips(layoutId: Int, autoDismissTime: Int) {
        showCenterTips(layoutId)
        activity!!.window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun showCenterTips(layoutId: Int, autoDismissTime: Int, callback: TipDialogCallback?) {
        showCenterTips(layoutId)
        activity!!.window.decorView.postDelayed({
            if (tipDialog != null) {
                tipDialog!!.dismiss()
                callback?.onDismiss()
            }
        }, autoDismissTime.toLong())
    }

    fun hideCenterTips() {
        if (tipDialog != null) {
            tipDialog!!.dismiss()
        }
    }

    fun showToastTop(msg: String?) {
        ToastUtils.setGravity(
            Gravity.TOP or Gravity.CENTER,
            0,
            mBaseContent!!.height / 2 - 20
        ) //200
        ToastUtils.showShort(msg)
    }

    fun showToastTop(res: Int) {
        ToastUtils.setGravity(
            Gravity.TOP or Gravity.CENTER,
            0,
            mBaseContent!!.height / 2 - 20
        ) //200
        ToastUtils.showShort(res)
    }


    fun showToastCenter(msg: String) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(msg)
    }

    fun showToastCenter(res: Int) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(res)
    }

    private val isCenterTipsShowing: Boolean
        private get() = tipDialog != null && tipDialog!!.isShowing

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        if (isCenterTipsShowing) {
            hideCenterTips()
            mViewModel = null
        }
        //移除监听
        if (mViewModel != null && mViewModel?.msgLiveData != null) {
            mViewModel?.msgLiveData?.removeObservers(viewLifecycleOwner)
            mViewModel?.refreshLiveData?.removeObservers(viewLifecycleOwner)
        }
        super.onDestroyView()
    }

    enum class TipsType(val value: Int) {
        NOTHING(0), LOADING(1), SUCCESS(2), ERROR(3), INFO(4);

    }

    interface TipDialogCallback {
        fun onDismiss()
    }

    companion object {
        @JvmField
        val TAG = BaseFragmentWithViewModel::class.java.canonicalName
    }
}