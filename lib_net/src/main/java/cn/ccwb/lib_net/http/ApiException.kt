package cn.ccwb.lib_net.http

/**
 * Api 异常
 */
class ApiException(var code: Int, msg: String) : Exception(msg) {

    override fun toString(): String {
        return "ApiException{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                '}'
    }
}