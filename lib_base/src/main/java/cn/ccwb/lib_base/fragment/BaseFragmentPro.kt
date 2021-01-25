package cn.ccwb.lib_base.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import cn.ccwb.lib_base.databinding.LibUiBaseFragmentBinding
import com.gyf.immersionbar.ImmersionBar

/**
 * BaseFragmentPro
 */
abstract class BaseFragmentPro : BaseFragment() {

    @JvmField
    protected var mRootViewBinding: LibUiBaseFragmentBinding? = null
    protected var mBaseAppbar: View? = null
    protected var mBaseContent: View? = null
    protected abstract fun initToolBar(inflater: LayoutInflater?): View?
    protected abstract fun addContentView(inflater: LayoutInflater?): View?
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    private var isFirstLoadData = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootViewBinding = LibUiBaseFragmentBinding.inflate(inflater, container, false)
        mBaseContent = addContentView(layoutInflater)
        mBaseAppbar = initToolBar(layoutInflater)
        if (mBaseContent != null) {
            mRootViewBinding!!.baseFragmentContentContainer.addView(mBaseContent)
        }
        if (mBaseAppbar != null) {
            mRootViewBinding!!.baseFragmentAppbarContainer.addView(mBaseAppbar)
        }
        return mRootViewBinding!!.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

        initListener()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoadData) {
            initData()
            isFirstLoadData = false
        }
    }

    fun fillStatusBar(statusBarColor: Int) {
        val params =
            mRootViewBinding!!.baseFragmentStatusContainer.layoutParams as LinearLayout.LayoutParams
        params.height = ImmersionBar.getStatusBarHeight(mActivity!!)
        mRootViewBinding!!.baseFragmentStatusContainer.layoutParams = params
        mRootViewBinding!!.baseFragmentStatusContainer.setBackgroundColor(
            ContextCompat.getColor(requireActivity(), statusBarColor)
        )

    }

    companion object {
        val TAG = BaseFragmentPro::class.java.simpleName
    }
}