package cn.ccwb.lib_base.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils


class RecyclerViewAtViewPager2 : RecyclerView {
    constructor(@NonNull context: Context?) : super(context!!) {}
    constructor(@NonNull context: Context?, @Nullable attrs: AttributeSet?) : super(
        context!!,
        attrs) {}

    constructor(
        @NonNull context: Context?,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {}

    private var startX = 0
    private var startY = 0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = Math.abs(endX - startX)
                val disY = Math.abs(endY - startY)
//                LogUtils.e(
//                    "DispatchTouchEvent disX=$disX; disY$disY; canScrollHorizontally(startX - endX) = " + canScrollHorizontally(
//                        startX - endX
//                    ) + "; canScrollVertically(startY - endY)" + canScrollVertically(startY - endY)
//                )
                if (disX > disY) {
                    //如果是纵向滑动，告知父布局不进行时间拦截，交由子布局消费，　requestDisallowInterceptTouchEvent(true)
                    parent.requestDisallowInterceptTouchEvent(canScrollHorizontally(startX - endX))
                } else {
                    parent.requestDisallowInterceptTouchEvent(canScrollVertically(startX - endX))
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                false
            )
        }
        return super.dispatchTouchEvent(ev)
    }
}