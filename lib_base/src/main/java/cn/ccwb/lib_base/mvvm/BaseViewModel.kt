package cn.ccwb.lib_base.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner

/**
 * 存在使用该viewmodel的界面在再次进入时候
 * showLoading和hideLoading不管用 待查实
 *
 * @param <V>
</V> */

abstract class BaseViewModel<V : IViewPro?>(application: Application) : AndroidViewModel(application) {
    @JvmField
    protected var mView: V? = null
    @JvmField
    protected var mLifecycleOwner: LifecycleOwner? = null
    @JvmField
    protected var mRefreshLiveData: NoCacheMutableLiveData<BaseTipsModel?>

    var msgLiveData: NoCacheMutableLiveData<String>
        protected set

    fun attachView(iViewPro: IViewPro?) {
        if (iViewPro != null) {
            mView = iViewPro as V
        }
    }

    fun attachLifecycleOwner(lifecycleOwner: LifecycleOwner?) {
        mLifecycleOwner = lifecycleOwner
    }

    open val refreshLiveData: NoCacheMutableLiveData<BaseTipsModel?>?
        get() = mRefreshLiveData

    /**
     * NOTHING(0),
     * LOADING(1),
     * SUCCESS(2),
     * ERROR(3),
     * INFO(4);
     */
    class BaseTipsModel private constructor(var mTipsType: Int, var msg: String, var show: Boolean) {

        companion object {
            const val NOTHING = 0
            const val LOADING = 1
            const val SUCCESS = 2
            const val INFO = 3
            const val HIDE = 4
            @JvmStatic
            fun loading(msg: String): BaseTipsModel {
                return BaseTipsModel(LOADING, msg, true)
            }

            fun success(msg: String): BaseTipsModel {
                return BaseTipsModel(SUCCESS, msg, true)
            }

            fun info(msg: String): BaseTipsModel {
                return BaseTipsModel(INFO, msg, true)
            }

            fun nothing(): BaseTipsModel {
                return BaseTipsModel(NOTHING, "", true)
            }

            fun hideLoading(): BaseTipsModel {
                return BaseTipsModel(HIDE, "", false)
            }

            @JvmStatic
            fun hideLoading(msg: String): BaseTipsModel {
                return BaseTipsModel(HIDE, msg, false)
            }
        }

    }

    companion object {
        fun getNullValue(obj: String?): String {
            return obj ?: ""
        }
    }

    init {
        mRefreshLiveData = NoCacheMutableLiveData()
        msgLiveData = NoCacheMutableLiveData()
    }
}