package cn.ccwb.cloud.restaurant.config

import android.content.Context
import cn.ccwb.cloud.restaurant.application.RestaurantAppContext
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig

class RestaurantModuleConfig : ModuleConfig {
    override fun injectAppLifecycle(context: Context): AppLifecycle {
        return RestaurantAppContext.INSTANCE!!
    }

}