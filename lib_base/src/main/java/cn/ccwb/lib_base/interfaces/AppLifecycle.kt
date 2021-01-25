package cn.ccwb.lib_base.interfaces

import android.app.Application
import android.content.Context

/**
 * AppLifecycle
 */
 interface AppLifecycle {

    fun attachBaseContext(context: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}