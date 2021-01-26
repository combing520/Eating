package cn.ccwb.cloud.order.config

import android.content.Context
import cn.ccwb.cloud.order.appliction.OrderAppContext
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig

class OrderModuleConfig : ModuleConfig {
    override fun injectAppLifecycle(context: Context): AppLifecycle {
        return OrderAppContext.INSTANCE!!
    }

}