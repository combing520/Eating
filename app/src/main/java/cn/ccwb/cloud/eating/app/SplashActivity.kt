package cn.ccwb.cloud.eating.app

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
//import com.seagazer.animlogoview.AnimLogoView
import com.tbruyelle.rxpermissions2.RxPermissions
import cn.ccwb.lib_base.utils.PermissionUtil
import cn.ccwb.lib_base.utils.PermissionUtil.RequestPermission
import com.blankj.utilcode.util.LogUtils

/**
 * 闪屏页
 */
class SplashActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

//    private lateinit var logo: AnimLogoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
//        logo = findViewById(R.id.animLogo)
        //权限请求
        requestPermission(permissions)
    }

    private fun requestPermission(permissionsAll: Array<String>) {
        PermissionUtil.requestPermission(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                goMain()
            }

            override fun onRequestPermissionFailure(permissions: List<String>?) {
                goMain()
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>?) {
                goMain()
            }
        }, RxPermissions(this), *permissionsAll)
    }


    private fun playAnimation() {
//        logo.addOffsetAnimListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator?) {
//                super.onAnimationEnd(animation)
//                goMain()
//            }
//
//        })
//        logo.startAnimation()
    }

    /**
     * 跳转到首页
     */
    private fun goMain() {
        ARouter.getInstance()
            .build(RouterPath.PAGE_MAIN)
            .withTransition(R.anim.fade_in, R.anim.fade_out)
            .navigation(this, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    finish()
                }
            })
    }
}