package cn.ccwb.lib_net.path

class WePath {
    @JvmField
    var path: String
    @JvmField
    var auth: Boolean

    constructor(path: String) {
        this.path = path
        auth = false
    }

    constructor(path: String, auth: Boolean) {
        this.path = path
        this.auth = auth
    }

    constructor() {
        path = ""
        auth = false
    }

    fun getFormattedUrl(vararg arg: Any?): String {
        return String.format(path, *arg)
    }
}