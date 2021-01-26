package cn.ccwb.cloud.usercenter.config

import android.content.Context
import cn.ccwb.cloud.usercenter.application.UserCenterAppContext
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig

class UserCenterModuleConfig : ModuleConfig {
    override fun injectAppLifecycle(context: Context): AppLifecycle {
        return UserCenterAppContext.INSTANCE!!
    }

}