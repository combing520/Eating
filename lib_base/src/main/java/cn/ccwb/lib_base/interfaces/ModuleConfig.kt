package cn.ccwb.lib_base.interfaces

import android.content.Context

/**
 * module 配置
 */
open interface ModuleConfig {
    fun injectAppLifecycle(context: Context): AppLifecycle
}