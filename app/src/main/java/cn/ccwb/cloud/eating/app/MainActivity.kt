package cn.ccwb.cloud.eating.app

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import cn.ccwb.lib_base.aop.click.AopOnclick
import cn.ccwb.lib_base.utils.GlideUtils
import cn.ccwb.lib_service.RouterPath
import cn.ccwb.lib_service.map.LocationManager
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocation
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 首页
 */
@Route(path = RouterPath.PAGE_MAIN)
class MainActivity : AppCompatActivity() {
    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initStatusBarInfo()
        initView()
        initData()
    }

    private fun initStatusBarInfo() {
        ImmersionBar.with(this)
            .statusBarDarkFont(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)
//            .statusBarColor(R.color.color_gray)
            .init()
    }

    private fun initView() {
        recommendTv.setOnClickListener {
            val scale = recommendTv.textScaleX
            LogUtils.e("scale = $scale")
            if (scale == 1.0f) {
                recommendTv.textScaleX = 1.3f
            } else {
                recommendTv.textScaleX = 1.0f
            }
        }
        discount_ll.setOnClickListener {
            goToPage(MenuType.TYPE_DISCOUNT, "优惠套餐", "", true)
        }
        near_tickets_ll.setOnClickListener {
            goToPage(MenuType.TYPE_TICKET, "附近好券", "", true)
        }
        userAvatarImg.setOnClickListener {
            goToPage(MenuType.TYPE_USER, "用户中心", "", true)
        }
        restaurant_list.setOnClickListener {
            goToPage(MenuType.TYPE_RESTAURANT, "餐厅排行", "", true)
        }

    }

    private fun initData() {
        val picPath =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fs15.sinaimg.cn%2Fmw690%2F006gYLVIzy75TtA9RGmae%26690&refer=http%3A%2F%2Fs15.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613727723&t=33b422ea58654c04015818f49b505f71"

        val biscuitsPath =
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3571520708,907247738&fm=26&gp=0.jpg"
        val paperPath =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcbu01.alicdn.com%2Fimg%2Fibank%2F2015%2F896%2F264%2F2055462698_2091694850.400x400.jpg&refer=http%3A%2F%2Fcbu01.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613892242&t=72d7ff04cd7d3eaed56025e800a5f2ff"

        val goods1Path =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fphotoblog%2F4%2F4%2F3%2F6%2F4436387%2F200911%2F8%2F1257687527836_mthumb.jpg&refer=http%3A%2F%2Fimg.pconline.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613894626&t=03560c0acd03d3aada34de3013850b1d"
        val goods2Path =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.to8to.com%2Fcase%2F1511%2F07%2F20151107_8704abe48ae6a2082920wtt45gsqawqs.jpg&refer=http%3A%2F%2Fpic.to8to.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613894626&t=0235df7b50d4a78144514815770a33a2"
        val goods3Path =
            "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/baike/pic/item/a8773912b31bb051ec066d183d7adab44aede0e4.jpg"

        val userAvatarPath =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F13%2F146310949475222613.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614236052&t=44dc308424a03f372dd9ce15109ea553"

        GlideUtils.loadNetPic(
            picPath,
            this@MainActivity,
            homeBannerImg,
            R.drawable.img_default_banner
        )
        GlideUtils.loadNetPic(biscuitsPath, this, biscuitsImg, R.drawable.img_default_banner)
        GlideUtils.loadNetPic(paperPath, this@MainActivity, paperImg, R.drawable.img_default_banner)

        GlideUtils.loadNetPic(
            goods1Path,
            this@MainActivity,
            goods01Img,
            R.drawable.img_default_banner
        )
        GlideUtils.loadNetPic(
            goods2Path,
            this@MainActivity,
            goods02Img,
            R.drawable.img_default_banner
        )
        GlideUtils.loadNetPic(
            goods3Path, this@MainActivity, goods03Img, R.drawable.img_default_banner
        )
        GlideUtils.loadNetPic(
            userAvatarPath, this@MainActivity, userAvatarImg, R.drawable.img_default_menu
        )
    }

    @AopOnclick
    private fun goToPage(enumType: MenuType, pathName: String, id: String, fitSystem: Boolean) {
        if (RouterPath.matchName2Path(pathName).equals(RouterPath.ERROR_PAGE)) {
            ToastUtils.showShort("该功能正在开发中...")
        } else {
            //其他的
            val route = ARouter.getInstance()
            var postCard: Postcard? = null
            when (enumType) {
                MenuType.TYPE_DISCOUNT -> {
                    postCard = route.build(RouterPath.PAGE_GUIDE_GOODS)
                }
                MenuType.TYPE_TICKET -> {
                    postCard = route.build(RouterPath.PAGE_GUIDE_TICKETS)
                }
                MenuType.TYPE_USER -> {
                    postCard = route.build(RouterPath.PAGE_GUIDE_USERCENTER)
                }
                MenuType.TYPE_RESTAURANT -> {
                    postCard = route.build(RouterPath.PAGE_GUIDE_RESTAURANT)
                }
            }
            postCard?.withString(RouterPath.TAG_PATH_FRAGMENT, RouterPath.matchName2Path(pathName))
                ?.withString(RouterPath.TAG_ID, id)
                ?.withBoolean(RouterPath.TAG_FIT_SYSTEM, fitSystem)
                ?.navigation()
        }
    }

    private fun showToastCenter(msg: String) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(msg)
    }


    override fun onResume() {
        super.onResume()
        LocationManager.getInstance().startLocation()
        if (LocationManager.getInstance().location != null) {
            address.text = LocationManager.getInstance().location?.poiName
            LocationManager.getInstance().stopLocation()
        } else {

            LocationManager.getInstance().setLocationCallback { aMapLocation: AMapLocation? ->
                if (null != aMapLocation) {
                    address.text = LocationManager.getInstance().location?.poiName
                    LocationManager.getInstance().stopLocation()
                } else {
                    // TODO: 定位没有打开--需要提示用户
                    showToastCenter("请打开定位")
                }
            }
        }
    }

    /**
     * 处理物理按键返回事件
     */
    override fun onBackPressed() {
        val currentTime: Long = System.currentTimeMillis()
        if (currentTime - mLastClickTime >= 1500) {
            mLastClickTime = currentTime
            showToastCenter(resources.getString(R.string.str_double_click_quit))
        } else {
            AppUtils.exitApp()
        }
    }
}

enum class MenuType {
    TYPE_DISCOUNT,
    TYPE_TICKET,
    TYPE_USER,
    TYPE_RESTAURANT
}