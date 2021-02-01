package cn.ccwb.cloud.goods.mvvm

import android.app.Application
import cn.ccwb.cloud.goods.entity.CouponOrVoucherInfoEntity
import cn.ccwb.lib_base.mvvm.BaseViewModel
import cn.ccwb.lib_base.mvvm.BaseViewPro
import cn.ccwb.lib_base.mvvm.NoCacheMutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 优惠套餐
 */
class DiscountViewModel(application: Application) : BaseViewModel<BaseViewPro>(application) {


    private val mDiscountServiceImp: DiscountServiceImp = DiscountServiceImp.INSTANCE!!

    /**
     * 店铺优惠打折信息
     */
    var mDiscountGoodsList: NoCacheMutableLiveData<List<CouponOrVoucherInfoEntity>>

    init {
        mDiscountGoodsList = NoCacheMutableLiveData()
    }

    fun getGoodsDiscountComboInfo(type: String, category: Int, distance: Int, order: String) {
        mView?.showLoading("加载中...")
        mDiscountServiceImp.getGoodsDiscountComboInfo(type, category, distance, order)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(mLifecycleOwner)))
            .subscribe(object : Observer<List<CouponOrVoucherInfoEntity>> {
                override fun onComplete() {

                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(t: List<CouponOrVoucherInfoEntity>) {
                    mView?.hideLoading()
                    mDiscountGoodsList.value = t
                }
                override fun onError(e: Throwable) {
                    mView?.hideLoading()
                    mDiscountGoodsList.value = null
                    LogUtils.e("getGoodsDiscountComboInfo  onError " + e.message)
                }
            })
    }
}