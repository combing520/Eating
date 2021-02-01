package cn.ccwb.cloud.restaurant.fragment

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import cn.ccwb.cloud.restaurant.R
import cn.ccwb.cloud.restaurant.adapter.ShopListAdapter
import cn.ccwb.cloud.restaurant.application.RestaurantAppContext
import cn.ccwb.cloud.restaurant.databinding.LibRestaurantFragmentRankBinding
import cn.ccwb.cloud.restaurant.databinding.LibRestaurantRankListToolbarBinding
import cn.ccwb.cloud.restaurant.mvvm.RestaurantViewModel
import cn.ccwb.lib_base.fragment.BaseFragmentWithViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils

/**
 * 餐厅排行
 */
@Route(path = RouterPath.PAGE_RESTAURANT_RANK_LIST)
class RestaurantRankListFragment : BaseFragmentWithViewModel<RestaurantViewModel>(), BaseViewPro {

    private lateinit var mToolBar: LibRestaurantRankListToolbarBinding
    private lateinit var mView: LibRestaurantFragmentRankBinding
    private lateinit var mAdapter: ShopListAdapter

    override fun setViewModel(): Class<RestaurantViewModel>? {
        return RestaurantViewModel::class.java
    }

    override fun setApp(): Application? {
        return RestaurantAppContext.INSTANCE!!.mApplication
    }

    override fun addContentView(inflater: LayoutInflater?): View? {
        mView = LibRestaurantFragmentRankBinding.inflate(inflater!!)
        return mView.root
    }

    override fun initToolBar(inflater: LayoutInflater?): View? {
        mToolBar = LibRestaurantRankListToolbarBinding.inflate(inflater!!)
        return mToolBar.root
    }

    override fun initView() {
        mAdapter = ShopListAdapter(R.layout.lib_restaurant_rank_list_item, null)
        mView.shopRecycle.adapter = mAdapter
    }

    override fun initData() {
        mViewModel?.mShopInfoList?.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                LogUtils.e("不为空！！！")
                mAdapter.setList(it)

            } else {
                LogUtils.e("为空！！！")

            }
        })
        mViewModel?.getShopInfoList("",0,5000,"default",1)
    }

    override fun initListener() {
        mToolBar.baseFragmentBack.setOnClickListener {
            _mActivity.finish()
        }

        mAdapter.setOnItemClickListener{
                adapter, view, position ->
           val item =  mAdapter.getItem(position)
            goToDetail(item.id.toString())
        }
    }

    private fun  goToDetail(id:String){
        ARouter.getInstance().build(RouterPath.PAGE_GUIDE_RESTAURANT)
            .withString(RouterPath.TAG_PATH_FRAGMENT, RouterPath.matchName2Path("餐厅详情"))
            .withString(RouterPath.TAG_ID, id)
            .withBoolean(RouterPath.TAG_FIT_SYSTEM, true)
            .navigation()
    }

    override fun hideOtherView() {
    }


    override fun showMsg(s: String) {
        showToastTop(s)
    }

    override fun showLoading(s: String) {
        showCenterTips(s, TipsType.LOADING)
    }

    override fun hideLoading() {
        hideCenterTips()
    }


}