package cn.ccwb.lib_base.mvvm

/**
 * Author: yyg
 * Date: 2020/4/23 14:43
 * Description:
 */
interface BaseViewPro : IViewPro {
    fun showMsg(s: String)
    fun showLoading(s: String)
    fun hideLoading()
}