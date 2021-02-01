package cn.ccwb.lib_service.map;

import android.app.Application;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import cn.ccwb.lib_net.http.Constants;

/**
 * Description: 定位
 */
public class LocationManager {
    private static volatile LocationManager sLocationManager;
    private static Application mApplication;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocation mCurrentLocation;

    public static LocationManager getInstance() {
        if (sLocationManager == null) {
            synchronized (LocationManager.class) {
                if (sLocationManager == null) {
                    sLocationManager = new LocationManager();
                }
            }
        }
        return sLocationManager;
    }

    public static void init(Application application) {
        mApplication = application;
    }

    private LocationManager() {
        mlocationClient = new AMapLocationClient(mApplication);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);      //高精度模式
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn); //签到场景
        mLocationOption.setInterval(1000);
        mLocationOption.setNeedAddress(true);   //返回地址描述
        mlocationClient.setLocationOption(mLocationOption);
    }

    //启动监听
    public void startLocation() {
        mlocationClient.stopLocation();
        mlocationClient.startLocation();
    }

    //结束监听
    public void stopLocation() {
        mlocationClient.stopLocation();
    }

    //定位回调
    public void setLocationCallback(final LocationCallback callback) {
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                    mCurrentLocation = aMapLocation;

                    Constants.INSTANCE.setAD_CODE(mCurrentLocation.getAdCode());
                    Constants.INSTANCE.setCW_LATITUDE(mCurrentLocation.getLatitude());
                    Constants.INSTANCE.setCW_LONGITUDE(mCurrentLocation.getLongitude());

                    callback.onLocation(aMapLocation);
                } else {
                    callback.onLocation(null);
                }
            }
        });
    }

    public interface LocationCallback {
        void onLocation(AMapLocation aMapLocation);
    }

    public AMapLocation getLocation() {
        if (mCurrentLocation != null) {
            return mCurrentLocation;
        }
        return null;
    }
}
