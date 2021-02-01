package cn.ccwb.lib_base.utils

import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*

public class PermissionUtil private constructor() {
    interface RequestPermission {
        /**
         * 权限请求成功
         */
        fun onRequestPermissionSuccess()

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailure(permissions: List<String>?)

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>?)
    }

    companion object {
        const val TAG = "Permission"
        fun requestPermission(requestPermission: RequestPermission, rxPermissions: RxPermissions, vararg permissions: String) {
            if (permissions == null || permissions.size == 0) {
                return
            }
            val needRequest: MutableList<String> = ArrayList()
            for (permission in permissions) { //过滤调已经申请过的权限
                if (!rxPermissions.isGranted(permission)) {
                    needRequest.add(permission)
                }
            }
            if (needRequest.isEmpty()) { //全部权限都已经申请过，直接执行操作
                requestPermission.onRequestPermissionSuccess()
            } else { //没有申请过,则开始申请
                rxPermissions
                    .requestEach(*needRequest.toTypedArray())
                    .buffer(permissions.size)
                    .subscribe { permissions ->
                        val failurePermissions: MutableList<String> = ArrayList()
                        val askNeverAgainPermissions: MutableList<String> = ArrayList()
                        for (p in permissions) {
                            if (!p.granted) {
                                if (p.shouldShowRequestPermissionRationale) {
                                    failurePermissions.add(p.name)
                                } else {
                                    askNeverAgainPermissions.add(p.name)
                                }
                            }
                        }
                        if (failurePermissions.size > 0) {
                            requestPermission.onRequestPermissionFailure(failurePermissions)
                        }
                        if (askNeverAgainPermissions.size > 0) {
                            requestPermission.onRequestPermissionFailureWithAskNeverAgain(askNeverAgainPermissions)
                        }
                        if (failurePermissions.size == 0 && askNeverAgainPermissions.size == 0) {
                            requestPermission.onRequestPermissionSuccess()
                        }
                    }
            }
        }
    }

    init {
        throw IllegalStateException("you can't instantiate me!")
    }
}