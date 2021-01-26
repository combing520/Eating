package cn.ccwb.lib_base.aop.click

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class AopClickAspect {

    // 此处为 定义的注解的绝对路径
    @Pointcut("execution(@cn.ccwb.lib_base.aop.click.click.AopOnclick * *(..))")
    fun methodAnnotated() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        // 取出方法的注解
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        if (!method.isAnnotationPresent(AopOnclick::class.java)) {
            return
        }
        val aopOnclick = method.getAnnotation(AopOnclick::class.java)
        // 判断是否快速点击
        if (!AopClickUtils.isFastDoubleClick(aopOnclick.value)) {
            Log.e("xzl", "正常点击")
            // 不是快速点击，执行原方法
            joinPoint.proceed()
        } else {
            Log.e("xzl", "快速点击")
        }


    }
}