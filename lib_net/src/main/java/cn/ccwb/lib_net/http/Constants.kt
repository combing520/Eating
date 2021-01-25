package cn.ccwb.lib_net.http

object Constants {

    /**
     * 访问终端
     */
    const val CW_CLIENT_STR: String = "cw_client"
    const val CW_CLIENT: String = "android"

    /**
     * 访问设备
     */
    const val CW_DEVICE_STR: String = "cw_device"

    /**
     * 访问设备的系统
     */
    const val CW_OS: String = "cw_os"

    /**
     *  设备类型
     */
    const val CW_MACHINE_TYPE: String = "app"

    /**
     * 设备唯一码
     */
    const val CW_MACHINE_ID_STR: String = "cw_machine_id"
    var CW_MACHINE_ID: String = ""

    /**
     * 国家
     */
    const val CW_COUNTRY_STR: String = "中国"
    var CW_COUNTRY: String = ""

    /**
     * 省会
     */
    const val CW_PROVINCE_STR: String = "cw_province"
    var CW_PROVINCE: String = "云南省"

    /**
     * 城市
     */
    const val CW_CITY_STR: String = "cw_city"
    var CW_CITY: String = "昆明市"

    /**
     * 区域
     */
    const val CW_AREA_STR: String = "cw_area"
    var CW_AREA: String = "五华区"

    /**
     * 经度
     */
    const val CW_LONGITUDE_STR: String = "cw_longitude"
    var CW_LONGITUDE: Double = 102.696104

    /**
     * 纬度
     */
    const val CW_LATITUDE_STR: String = "cw_latitude"
    var CW_LATITUDE: Double = 25.041404

    /**
     * IP
     */
    const val CW_IP_STR: String = "cw_ip"
    var IP: String = ""

    /**
     * 设备型号
     */
    const val CW_DEVICEMODEL_STR: String = "cw_devicemodel"
    var CW_DEVICEMODEL: String = ""

    /**
     * 版本信息
     */
    const val CW_VERSION_STR: String = "cw_version"

    /**
     * 网络型号
     */
    var CW_NETWORKTYPE = "WIFI"


    /**
     * 话题 的 id
     */
    var TOPIC_ID = 0

    /**
     * title Tag
     */
    const val TAG_TITLE = "title"

    /**
     * id Tag
     */
    const val TAG_ID = "id"

    /**
     * url Tag
     */
    const val TAG_URL = "url"

    /**
     * summary Tag
     */
    const val TAG_SUMMARY = "summary"

    /**
     * topic Id
     */
    const val TAG_TOPIC = "topicId"

    /**
     * 普通新闻类型
     */
    const val TYPE_NEWS_NORMAL = "news"

    /**
     * 新闻组类型
     */
    const val TYPE_NEWS_GROUP = "newslist"

    /**
     * 视频类型
     */
    const val TYPE_VIDEO_NORMAL = "video"

    /**
     * 视频组
     */
    const val TYPE_VIDEO_GROUP = "videogroup"

    /**
     * 图集类型
     */
    const val TYPE_PHOTO = "photo"

    /**
     * 直播类型
     */
    const val TYPE_LIVE = "live"

    /**
     * 活动类型
     */
    const val TYPE_URL = "url"

    /**
     * 专题类型
     */
    const val TYPE_TOPIC = "topic"

    /**
     * 图集组
     */
    const val TYPE_PHOTO_GROUP = "photogroup"

    /**
     * 焦点类型
     */
    const val TYPE_FOCUS = "focus"

    /**
     * 组新闻
     */
    const val TYPE_TEAM_GROUP = "teamgroup"

    /**
     * 普通新闻组
     */
    const val TYPE_NEWS_NORMAL_GROUP = "normaltopic"

    /**
     * 采集喜欢
     */
    const val TYPE_GUESS_LIKE = "GuessLike"

    /**
     * 动新闻
     */
    const val TYPE_DYNAMIC_NEWS = "DynamicNews"

    /**
     * 拍客照片
     */
    const val TYPE_SHOOT_PHOTO = "shootphoto"

    /**
     * 拍客视频
     */
    const val TYPE_SHOOT_VIDEO = "shootvideo"

    /**
     * 功能区
     */
    const val TYPE_FUNCTIONS = "functions"

    /**
     * 滚动新闻
     */
    const val TYPE_ROLLING_NEWS = "RollingNews"

    /**
     * 应用号分类
     */
    const val TYPE_CHANNEL_GROUP = "ChannelGroup"

    /**
     * 应用号查看更多
     */
    const val TYPE_CHANNEL = "channel"

    /**
     * 更多
     */
    const val TYPE_MORE = "more"

    /**
     * 突发
     */
    const val TYPE_SUDDEN = "sudden"

    /**
     * shareContent Tag
     */
    const val TAG_SHARE_CONTENT = "shareContent"

    /**
     * group_id Tag
     */
    const val TAG_GROUP_ID = "group_id"

    /**
     * 扫描结果
     */
    const val TAG_RESULT_SCAN = "scanResult"


    const val TYPE_NEWS_NORMAL_ITEM_VIEW = 100 // 普通新闻

    const val TYPE_NEWS_GROUP_ITEM_VIEW = 200 // 新闻组

    const val TYPE_VIDEO_ITEM_VIEW = 300 // 视频

    const val TYPE_VIDEO_GROUP_ITEM_VIEW = 400 // 视频组

    const val TYPE_NEWS_ALBUM_ITEM_VIEW = 500 // 图集

    const val TYPE_NEWS_LIVE_ITEM_VIEW = 600 // 直播

    const val TYPE_NEWS_SUDDEN_ITEM_VIEW = 700 // 突发

    const val TYPE_NEWS_URL_ITEM_VIEW = 800 //活动

    const val TYPE_SPECIAL_ITEM_VIEW = 900 //专题

    const val TYPE_ALBUM_GROUP_ITEM_VIEW = 1000 //图组集

    const val TYPE_NEWS_FOCUS_ITEM_VIEW = 1100 // 焦点

    const val TYPE_NEWS_TEAM_GROUP_ITEM_VIEW = 1200 //组新闻

    const val TYPE_LOAD_MORE_APPS_ITEM_VIEW = 1300 // 应用号加载更多【或者普通新闻加载更多】

    const val TYPE_APPS_ITEM_VIEW = 1400 // 应用号

    const val TYPE_CLASSIFY_APPS_ITEM_VIEW = 1500 // 应用号分类

    const val TYPE_RECOMMEND_ITEM_VIEW = 1600 //猜你喜欢

    const val TYPE_NEWS_ROLL_ITEM_VIEW = 1700 // 专属焦点 /滚动新闻

    const val TYPE_NEWS_FUNCTIONS_ITEM_VIEW = 1800 //功能区

    const val TYPE_NEWS_ACTIVE_ITEM_VIEW = 1900 // 动新闻

    const val TYPE_NEWS_TOPIC_NORMAL_ITEM_VIEW = 2000 // 普通新闻的 专题(长得和普通新闻一样； 但是逻辑是 专题的)

    const val TYPE_NEWS_PIC_PAIKEW_ITEM_VIEW = 2100 // 拍客图片

    const val TYPE_NEWS_VIDEO_PAIKEW_ITEM_VIEW = 2200 // 拍客视频

    const val TYPE_NEWS_OTHER_ITEM_VIEW = 2300 // 其他类型

}