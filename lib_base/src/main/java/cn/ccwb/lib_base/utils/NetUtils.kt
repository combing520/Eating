package cn.ccwb.lib_base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


class NetUtils {

    companion object {
        /**
         * 获取网络信息
         */
        fun getNetInfo(context: Context): String {
            val cm: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT < 23) {
                cm.activeNetworkInfo?.let {
                    if (!it.isAvailable) return@let "无网络"
                    else return@let it.typeName
                } ?: "WIFI"
            } else {
                cm.getNetworkCapabilities(cm.activeNetwork)?.let {
                    when {
                        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return@let "WIFI"
                        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return@let "移动数据"
                        else -> return@let "无网络"
                    }
                } ?: "WIFI"
            }
        }


        /**
         * 判断网络连接状态
         *
         * @param context 上下文对象
         * @return 网络是否连接
         */
        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager
                    .activeNetworkInfo

                if (networkInfo != null) {
                    return networkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 网络状态是否为 WIFI
         *
         * @param context 上下文对象
         * @return 是否 WIFI 网络
         */
        fun isWifiConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 网络状态是否为数据流量
         *
         * @param context 上下文对象
         * @return 是否为数据流量
         */
        fun isMobileConnected(context: Context?): Boolean {
            if (context != null) {
                val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable
                }
            }
            return false
        }

        /**
         * 获取网络连接类型
         *
         * @param context 上下文对象
         * @return 网络连接状态
         */
        fun getConnectedType(context: Context?): Int {
            if (context != null) {
                val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager
                    .activeNetworkInfo
                if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                    return mNetworkInfo.type
                }
            }
            return -1
        }


        /**
         * 获取IP地址
         *
         * @return ip 地址
         */
        fun getIpAddressString(): String? {
            try {
                val enNetI: Enumeration<NetworkInterface> = NetworkInterface
                    .getNetworkInterfaces()
                while (enNetI.hasMoreElements()) {
                    val netI: NetworkInterface = enNetI.nextElement()
                    val enumIpAddr: Enumeration<InetAddress> = netI
                        .getInetAddresses()
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress: InetAddress = enumIpAddr.nextElement()
                        if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (e: SocketException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}