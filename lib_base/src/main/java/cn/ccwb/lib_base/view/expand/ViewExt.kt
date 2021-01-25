package cn.ccwb.lib_base.view.expand
import android.view.View
internal fun View.dpToPx(dp: Int): Float {
    val scale = resources.displayMetrics.density
    return dp * scale
}