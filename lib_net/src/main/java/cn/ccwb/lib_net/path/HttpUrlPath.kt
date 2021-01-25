package cn.ccwb.lib_net.path

import java.util.ArrayList

object HttpUrlPath {
    @JvmField
    var sWePaths: MutableList<WePath> = ArrayList()

    /**
     * 开机广告
     */
    @JvmField
    val ADVERTISEMENT_SPLASH = WePath("v1/ad/getAdStartupLists", true)


    //=============首页 begin=============//
    /**
     * 获取首页 顶部title
     */
    @JvmStatic
    val TITLE_HOME = WePath("v1/index/getIndexTopChannelLists", true)

    /**
     * 首页 新闻列表
     */
    @JvmStatic
    val LIST_NEWS_HOME = WePath("v1/index/getIndexNewsLists", true)

    /**
     * 首页推荐新闻
     */
    @JvmStatic
    val LIST_NEWS_RECOMMEND_HOME = WePath("v1/news/recommendFeeds", true)

    /**
     * 首页的 【滚动新闻】---专属焦点
     */
    @JvmStatic
    val LIST_NEWS_ROLLING = WePath("v1/news/exclusiveFeeds", true)

    /**
     * Home 的 新闻详情
     */
    @JvmStatic
    val DETAIL_NEWS_HOME = WePath("v1/news/getNewsDetail", true)

    /**
     * 专题新闻列表
     */
    @JvmStatic
    val LIST_NEWS_TOPIC = WePath("v1/news/getTopicLists", true)

    /**
     * 新闻详情中 的推荐新闻 和相关新闻
     */
    @JvmStatic
    val DETAIL_NEWS_RELATE = WePath("v1/news/getNewsDetailRelated", true)


    //=============首页 end=============//


    //=============视频 begin=============//

    /**
     * 视频的分类
     */
    @JvmStatic
    val LIST_TITLE_CLASSFIY = WePath("v1/channel/getDynamicNewsChannal", false)

    /**
     * 视频列表
     */
    @JvmStatic
    val LIST_VIDEO = WePath("v1/news/getDynamicNewsLists", false)
    //=============视频 end=============//


    //=============应用号 begin=============//
    /**
     * 应用号 分类
     */
    @JvmStatic
    val LIST_TITLE_CLASSIFY_APPS = WePath("v1/channel/getChannelGroupRecommendLists", false)

    @JvmStatic
    val LIST_APPS = WePath("v1/channel/getChanelLists", false)

    //=============应用号 end=============//

    /**
     * 视频详情
     */
    @JvmStatic
    val DETAIL_VIDEO = WePath("v1/news/getNewsVideoDetail", true)

    /**
     * 视频组详情
     */
    val DETAIL_VIDEO_GROUP = WePath("v1/news/getNewsVideoGroupDetail", true)
    //=============视频 begin=============//


    //=============应用号 begin=============//
    @JvmField
    val LIST_APPS_RECOMMEND = WePath("v1/channel/getChannelGroupLists", true)

    @JvmField
    val LIST_BANNER_APPS = WePath("v1/channel/getChannelScrollLists", true)

    /**
     * 应用号详情
     */
    @JvmField
    val DETAIL_APPS = WePath("v1/channel/getChannelDetail", true)

    //=============应用号 end=============//


    //=============拍客 begin=============//
    /**
     * 拍客视频列表
     */
    @JvmField
    val LIST_VIDEO_PAIKE = WePath("v1/shoot/getVideoList", true)

    /**
     * 拍客 照片列表
     */
    @JvmField
    val LIST_PICTURE_PAIKE = WePath("v1/shoot/getPhotoList", true)

    /**
     * 拍客照片详情
     */
    @JvmField
    val LIST_DETAIL_PHOTO_PAIKE = WePath("v1/shoot/getPhotoInfo", true)

    /**
     * 拍客详情中的列表
     */
    @JvmField
    val LIST_DETAIL_VIDEO_PAIKE = WePath("v1/shoot/getVideoInfoList", true)

    /**
     * 拍客热门标签
     */
    val LIST_TOPIC_HOT_PAIKE = WePath("v1/shoot/getHotTopic", true)

    /**
     * 拍客热门作品
     */
    val LIST_TOPIC_HOT_WORKS_PAIKE = WePath("v1/shoot/getHotTopicList", true)

    //=============拍客 end=============//

    init {
        sWePaths.add(ADVERTISEMENT_SPLASH)
    }
}