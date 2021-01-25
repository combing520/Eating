package cn.ccwb.lib_base.mvp

import androidx.lifecycle.LifecycleOwner

abstract class BasePresenter<V : IView?>(protected var mLifecycleOwner: LifecycleOwner) : IPresenter {

    protected var view: V? = null

    override fun attachView(iView: IView) {
        if (iView != null) {
            view = iView as V
        }
    }

    override fun detachView() {
    }
}