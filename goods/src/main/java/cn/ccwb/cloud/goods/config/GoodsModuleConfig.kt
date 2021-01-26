package cn.ccwb.cloud.goods.config

import android.content.Context
import cn.ccwb.cloud.goods.application.GoodsAppContext
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig

class GoodsModuleConfig : ModuleConfig {
        override fun injectAppLifecycle(context: Context): AppLifecycle {
            return GoodsAppContext.INSTANCE!!
        }

}