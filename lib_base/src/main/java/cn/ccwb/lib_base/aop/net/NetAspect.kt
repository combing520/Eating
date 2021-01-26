
package cn.ccwb.lib_base.aop.net
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class NetAspect {
    @Pointcut("execution(@ccn.ccwb.lib_base.aop.net.CheckNet * *(..))")
    fun methodAnnotated() {
    }

    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        val checkNet = signature.method.getAnnotation(CheckNet::class.java)
        Log.e("xzl", "!!!+++")
        if (checkNet != null) {
            val obj = joinPoint.`this`
            val context = getContext(obj)
            if (context != null) {
                if (NetUtils.isNetworkConnected(context)) {
                    joinPoint.proceed()
                    Log.e("xzl", "有网")
                } else {
                    Log.e("xzl", "没有网")
                }
            } else {
                Log.e("xzl", "context is null")
            }
        } else {
            Log.e("xzl", "checkNet为空！！！！")
        }
    }

    private fun getContext(objects: Any): Context? {
        return when (objects) {
            is Activity -> {
                objects as Activity
            }
            is Fragment -> {
                objects.requireActivity()
            }
            is View -> {
                objects.context
            }
            else -> null
        }
    }
}