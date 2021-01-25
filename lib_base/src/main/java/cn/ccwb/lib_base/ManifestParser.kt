package cn.ccwb.lib_base

import android.content.Context
import android.content.pm.PackageManager
import cn.ccwb.lib_base.interfaces.ModuleConfig
import java.util.*


class ManifestParser constructor(private val context: Context) {

    companion object {
        private const val MODULE_VALUE = "ConfigModule"
        private fun parseModule(className: String): ModuleConfig {
            val clazz: Class<*>
            try {
                clazz = Class.forName(className)
            } catch (e: Exception) {
                throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
            }
            val module: Any?
            try {
                module = clazz.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException(
                    "Unable to instantiate ConfigModule implementation for $clazz",
                    e
                )
            } catch (e: IllegalAccessException) {
                throw java.lang.RuntimeException(
                    "Unable to instantiate ConfigModule implementation for $clazz",
                    e
                )
            }
            if (module !is ModuleConfig) {
                throw  java.lang.RuntimeException("Expected instanceof ConfigModule, but found: $module")
            }
            return module
        }
    }


    fun parse(): List<ModuleConfig>? {
        val modules: MutableList<ModuleConfig> =
            ArrayList()
        try {
            val appInfo =
                context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA
                )
            if (appInfo.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData[key]) {
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw java.lang.RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }
        return modules
    }
}