package cn.ccwb.cloud.order.appliction

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import cn.ccwb.lib_base.interfaces.AppLifecycle
import java.lang.Exception

/**
 * 订单
 */
class OrderAppContext : AppLifecycle {
    lateinit var mApplication: Application

    companion object {
        private val TAG: String = OrderAppContext::class.java.simpleName

        @Volatile
        private var mAppContext: OrderAppContext? = null

        @JvmStatic
        val INSTANCE: OrderAppContext?
            get() {
                if (mAppContext == null) {
                    synchronized(OrderAppContext::class.java) {
                        if (mAppContext == null) {
                            mAppContext =
                                OrderAppContext()
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