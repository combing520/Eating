package cn.ccwb.cloud.goods.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import cn.ccwb.cloud.goods.application.DiscountAppContext
import cn.ccwb.cloud.goods.databinding.LibGoodsFragmentHomeBinding
import cn.ccwb.cloud.goods.databinding.LibGoodsFragmentToolbarBinding
import cn.ccwb.cloud.goods.mvvm.DiscountViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * 打折优惠 列表
 */
@Route(path = RouterPath.PAGE_GOODS_DISCOUNT)
class DiscountListFragment : BaseFragmentWithViewModel<DiscountViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibGoodsFragmentToolbarBinding
    private lateinit var mView: LibGoodsFragmentHomeBinding

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibGoodsFragmentToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibGoodsFragmentHomeBinding.inflate(inflater!!)
        return mView.root
    }


    override fun setViewModel(): Class<DiscountViewModel>? {
        return DiscountViewModel::class.java
    }

    override fun setApp(): Application? {
        return DiscountAppContext.INSTANCE!!.mApplication
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun hideOtherView() {
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }
    }

    override fun hideLoading() {
    }

    override fun showMsg(s: String) {
    }

    override fun showLoading(s: String) {
    }

}