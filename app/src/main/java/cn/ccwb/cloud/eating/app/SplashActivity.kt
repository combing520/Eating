package cn.ccwb.cloud.eating.app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.ccwb.lib_service.RouterPath
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.seagazer.animlogoview.AnimLogoView
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 闪屏页
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var logo: AnimLogoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        logo = findViewById(R.id.animLogo)
        playAnimation()
    }

    private fun playAnimation() {
        logo.addOffsetAnimListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                goMain()
            }

        })
        logo.startAnimation()
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