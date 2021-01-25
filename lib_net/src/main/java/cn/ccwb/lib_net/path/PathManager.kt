package cn.ccwb.lib_net.path

import java.util.*

class PathManager {
    companion object {
        private val TAG = PathManager::class.java.simpleName
        private var mInstance: PathManager? = null
        private val mPaths: MutableList<WePath> = ArrayList()
        @JvmStatic
        val instance: PathManager?
            get() {
                if (mInstance == null) {
                    synchronized(PathManager::class.java) {
                        if (mInstance == null) {
                            mInstance = PathManager()
                        }
                    }
                }
                return mInstance
            }

        init {
            mPaths.addAll(HttpUrlPath.sWePaths)
        }
    }

    fun isNeedToken(path: String): Boolean {
        for (p in mPaths) {
            if (p.path == path) {
                return p.auth
            }
        }
        return true
    }
}