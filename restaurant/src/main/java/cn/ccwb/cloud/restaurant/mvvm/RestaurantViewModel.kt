package cn.ccwb.cloud.restaurant.mvvm

import android.app.Application
import cn.ccwb.cloud.restaurant.entity.ShopInfoDetailEntity
import cn.ccwb.cloud.restaurant.entity.ShopInfoEntity
import cn.ccwb.lib_base.mvvm.BaseViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_base.mvvm.NoCacheMutableLiveData
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 用户中心model 中 数据操作类
 */
class RestaurantViewModel(application: Application) : BaseViewModel<BaseViewPro>(application) {
    private val mRestaurantServiceImp: RestaurantServiceImp = RestaurantServiceImp.INSTANCE!!

    /**
     * 商品列表信息
     */
    var mShopInfoList: NoCacheMutableLiveData<List<ShopInfoEntity>>

    val mShopInfoDetailEntity:NoCacheMutableLiveData<ShopInfoDetailEntity>
    init {
        mShopInfoList = NoCacheMutableLiveData()
        mShopInfoDetailEntity = NoCacheMutableLiveData()
    }

    /**
     * 商店列表
     */
    fun getShopInfoList(keywords: String?, category: Int, distance: Int, order: String, p: Int) {
        mView?.showLoading("加载中...")
        mRestaurantServiceImp.getShopInfoList(keywords, category, distance, order, p)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mLifecycleOwner)))
            .subscribe(object : Observer<List<ShopInfoEntity>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<ShopInfoEntity>) {
                    mView?.hideLoading()
                    mShopInfoList.value = t
                }

                override fun onError(e: Throwable) {
                    mView?.hideLoading()
                    mShopInfoList.value = null
                }
            })
    }

    /**
     * 获取商铺详情信息
     */
    fun getShopInfoDetail(id: String) {
        mView?.showLoading("加载中...")
        mRestaurantServiceImp.getShopInfoDetail(id)
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mLifecycleOwner)))
            ?.subscribe(object :Observer<ShopInfoDetailEntity?>{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ShopInfoDetailEntity) {
                    mView?.hideLoading()
                    mShopInfoDetailEntity.value = t
                }

                override fun onError(e: Throwable) {
                    mView?.hideLoading()
                    mShopInfoDetailEntity.value = null
                }

            })
    }
}