package cn.ccwb.lib_base

import android.app.Activity
import android.app.Application
import android.content.Context
import cn.ccwb.lib_base.interfaces.AppLifecycle
import cn.ccwb.lib_base.utils.SuperTextViewGlideEngine
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.coorchice.library.ImageEngine
import com.tencent.smtt.sdk.QbSdk
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.yokeyword.fragmentation.Fragmentation

open class BaseApp private constructor() : AppLifecycle {
    private lateinit var mApplication: Application

    companion object {
        private const val TAG: String = "BaseApp"
        private var INSTANCE: BaseApp? = null
            get() {
                if (field == null) {
                    field = BaseApp()
                }
                return field
            }

        @Synchronized
        fun get(): BaseApp {
            return INSTANCE!!
        }
    }

    override fun attachBaseContext(context: Context) {
    }

    override fun onCreate(application: Application) {
        mApplication = application
        initAutoSize()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)

        // 栈视图等功能，建议在Application里初始化
        Fragmentation.builder()
            // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
            .stackViewMode(Fragmentation.NONE)
            .debug(BuildConfig.DEBUG)
            .install()

        // 安装SuperTextView 加载图片的 引擎
        ImageEngine.install(SuperTextViewGlideEngine(application))

        Utils.init(application)
        ToastUtils.setBgColor(application.resources.getColor(R.color.colorToastBg))
        ToastUtils.setMsgColor(application.resources.getColor(R.color.white))

        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(application, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
            }

            override fun onViewInitFinished(p0: Boolean) {
            }

        })

//        //极光推送
//        JPushInterface.setDebugMode(true) // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(application) // 初始化 JPush

        //Bugly
//        Bugly.init(application, "a9f6452c70", false)

    }

    override fun onTerminate(application: Application) {
    }

    private fun initAutoSize() {
        AutoSize.checkAndInit(mApplication)
        AutoSizeConfig.getInstance()
            //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
            //如果没有这个需求建议不开启
            .setCustomFragment(true)
            //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
            //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
            .setExcludeFontScale(true).onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any?, activity: Activity?) {
                //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                //                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
                //                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
            }

            override fun onAdaptAfter(target: Any?, activity: Activity?) {
            }

        }
        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)
        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
        //在全面屏或刘海屏幕设备中, 获取到的屏幕高度可能不包含状态栏高度, 所以在全面屏设备中不需要减去状态栏高度，所以可以 setUseDeviceSize(true)
//                .setUseDeviceSize(true)
        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
//                .setBaseOnWidth(false)
        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())
    }
}