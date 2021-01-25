package cn.ccwb.lib_base

import android.app.Application
import android.content.Context
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.interfaces.ModuleConfig
import java.util.ArrayList

class AppDelegate(context: Context?) : AppLifecycle {

    private var mApplication: Application? = null
    private var mModules: List<ModuleConfig>? = ArrayList()
    private var mAppLifeCycles: MutableList<AppLifecycle>? = ArrayList()
    override fun attachBaseContext(base: Context) {
        //遍历 mAppLifeCycles, 执行所有已注册的 AppLifeCycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (lifecycle in mAppLifeCycles!!) {
            lifecycle.attachBaseContext(base)
        }
    }

    override fun onCreate(application: Application) {
        mApplication = application
        mModules = null
        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        for (lifecycle in mAppLifeCycles!!) {
            lifecycle.onCreate(mApplication!!)
        }
    }

    override fun onTerminate(application: Application) {
        if (mAppLifeCycles != null && mAppLifeCycles!!.size > 0) {
            for (lifecycle in mAppLifeCycles!!) {
                mApplication?.let { lifecycle.onTerminate(it) }
            }
        }
        mAppLifeCycles = null
        mApplication = null
    }

    init {
        //用反射, 将个模块 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        mModules = ManifestParser(context!!).parse()
        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (module in mModules!!) {
            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            mAppLifeCycles?.add(module.injectAppLifecycle(context))
        }
    }
}