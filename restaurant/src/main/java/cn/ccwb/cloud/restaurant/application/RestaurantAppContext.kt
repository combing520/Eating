package cn.ccwb.cloud.restaurant.application

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import cn.ccwb.lib_base.interfaces.AppLifecycle
import java.lang.Exception

class RestaurantAppContext : AppLifecycle {
    lateinit var mApplication: Application

    companion object {
        private val TAG: String = RestaurantAppContext::class.java.simpleName

        @Volatile
        private var mAppContext: RestaurantAppContext? = null

        @JvmStatic
        val INSTANCE: RestaurantAppContext?
            get() {
                if (mAppContext == null) {
                    synchronized(RestaurantAppContext::class.java) {
                        if (mAppContext == null) {
                            mAppContext =
                                RestaurantAppContext()
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