package cn.ccwb.cloud.tickets.config

import android.content.Context
import cn.ccwb.cloud.tickets.appliction.TicketsAppContext
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig

class TicketsModuleConfig : ModuleConfig {
    override fun injectAppLifecycle(context: Context): AppLifecycle {
        return TicketsAppContext.INSTANCE!!
    }

}