package cn.ccwb.cloud.eating.app.application
import android.app.Application
import android.content.Context
import cn.ccwb.lib_base.AppDelegate

//import com.jeremyliao.liveeventbus.LiveEventBus

class AppContext : Application() {

    private var mAppDelegate: AppDelegate? = null

    companion object {
        var mApplication: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        mAppDelegate?.onCreate(this)

//        LiveEventBus.config()
//            .autoClear(true)
//            .lifecycleObserverAlwaysActive(true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (mAppDelegate == null) {
            mAppDelegate = AppDelegate(base)
        }
        mAppDelegate?.attachBaseContext(base!!)
    }

    override fun onTerminate() {
        super.onTerminate()
        mAppDelegate?.onTerminate(this)
    }
}