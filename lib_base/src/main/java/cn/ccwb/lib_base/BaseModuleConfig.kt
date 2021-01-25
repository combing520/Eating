package cn.ccwb.lib_base

import android.content.Context
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig

class BaseModuleConfig  :ModuleConfig{
    override fun injectAppLifecycle(context: Context): AppLifecycle {
        return BaseApp.get()
    }
}