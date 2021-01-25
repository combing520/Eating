package cn.ccwb.cloud.goods.application

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import cn.ccwb.lib_base.interfaces.AppLifecycle
import java.lang.Exception

class DiscountAppContext : AppLifecycle {
     lateinit var mApplication: Application
    companion object {
        private val TAG: String = DiscountAppContext::class.java.simpleName

        @Volatile
        private var mAppContext: DiscountAppContext? = null

        @JvmStatic
        val INSTANCE: DiscountAppContext?
            get() {
                if (mAppContext == null) {
                    synchronized(DiscountAppContext::class.java) {
                        if (mAppContext == null) {
                            mAppContext =
                                DiscountAppContext()
                        }
                    }
                }
                return mAppContext
            }
    }

    override fun attachBaseContext(context: Context) {
    }

    override fun onCreate(application: Application) {
        mApplication = application
        val pid: Int = Process.myPid()
        val processName: String? = getAppName(pid, application)

        if (!(!processName.isNullOrEmpty() && processName.equals(
                application.packageName,
                ignoreCase = true
            ))
        ) {
            return
        }
    }

    override fun onTerminate(application: Application) {
    }

    private fun getAppName(pId: Int, application: Application): String? {
        var processName: String? = null
        val manager = application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val l: List<*> = manager.runningAppProcesses
        val i = l.iterator()
        var pm = application.packageManager

        while (i.hasNext()) {
            val info = i.next() as ActivityManager.RunningAppProcessInfo
            try {
                if (info.pid == pId) {
                    processName = info.processName
                    return processName
                }
            } catch (e: Exception) {
                return null
            }
        }
        return processName
    }
}