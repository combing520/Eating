package cn.ccwb.lib_service.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import cn.ccwb.lib_base.interfaces.AppLifecycle;
import cn.ccwb.lib_service.map.LocationManager;

/**
 * ServiceApp
 */
public class ServiceApp implements AppLifecycle {
    public static final String TAG = ServiceApp.class.getSimpleName();


    public static ServiceApp sServiceApp;

    public static ServiceApp getInstance() {
        if (sServiceApp == null) {
            synchronized (ServiceApp.class) {
                if (sServiceApp == null) {
                    sServiceApp = new ServiceApp();
                }
            }
        }
        return sServiceApp;
    }

    private ServiceApp() {
        Log.e(TAG, "ServiceApp: ");
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        //初始化数据库
//        AppDataBase.init(application);
        LocationManager.init(application);
    }

    @Override
    public void onTerminate(Application application) {

    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
