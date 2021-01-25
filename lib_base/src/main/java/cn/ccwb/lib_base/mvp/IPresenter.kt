package cn.ccwb.lib_base.mvp

interface IPresenter {

    /**
     * 注入View，使之能够与View相互响应
     */
    fun attachView(view: IView)

    /**
     * 释放资源，如果使用了网络请求
     */
    fun detachView()
}