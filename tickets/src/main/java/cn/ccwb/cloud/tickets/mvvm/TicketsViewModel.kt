package cn.ccwb.cloud.tickets.mvvm

import android.app.Application
import cn.ccwb.cloud.tickets.entity.CouponOrVoucherInfoEntity
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
 * 优惠券 model 中 数据操作类
 */
class TicketsViewModel(application: Application) : BaseViewModel<BaseViewPro>(application) {


    private val mTicketsServiceImp: TicketsServiceImp = TicketsServiceImp.INSTANCE!!

    /**
     *  优惠券/套餐 信息
     */
    var mDiscountGoodsList: NoCacheMutableLiveData<List<CouponOrVoucherInfoEntity>>

    init {
        mDiscountGoodsList = NoCacheMutableLiveData()
    }

    /**
     *  获取  优惠券/套餐 信息
     */
    fun getGoodsDiscountComboInfo(type: String, category: Int, distance: Int, order: String) {
        mView?.showLoading("加载中...")
        mTicketsServiceImp.getGoodsDiscountComboInfo(type, category, distance, order)
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