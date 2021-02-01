package cn.ccwb.lib_service.config;

import android.content.Context;

import cn.ccwb.lib_base.interfaces.AppLifecycle;
import cn.ccwb.lib_base.interfaces.ModuleConfig;
import cn.ccwb.lib_service.application.ServiceApp;


/**
 * ServiceModuleConfig
 */
public class ServiceModuleConfig implements ModuleConfig {
    @Override
    public AppLifecycle injectAppLifecycle(Context context) {
        return ServiceApp.getInstance();
    }
}
