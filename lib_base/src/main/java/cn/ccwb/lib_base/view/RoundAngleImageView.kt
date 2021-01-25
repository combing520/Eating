package cn.ccwb.lib_base.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.Nullable
import cn.ccwb.lib_base.R

/**
 * 圆角 ImageView
 */
@SuppressLint("AppCompatCustomView")
class RoundAngleImageView : ImageView {
    private var paint: Paint? = null
    private var paint2: Paint? = null

    // 默认的圆角宽度
    private var roundWidth = cornerSize

    // 默认圆角的高度
    private var roundHeight = cornerSize

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        if (null != attrs) {
            val a =
                context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView)
            roundWidth =
                a.getDimensionPixelSize(R.styleable.RoundAngleImageView_roundWidth, roundWidth)
            roundHeight =
                a.getDimensionPixelSize(R.styleable.RoundAngleImageView_roundHeight, roundHeight)
        } else {
            val density = context.resources.displayMetrics.density
            roundWidth = (roundWidth * density).toInt()
            roundHeight = (roundHeight * density).toInt()
        }
        paint = Paint()
        paint!!.color = Color.WHITE
        paint!!.isAntiAlias = true
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        paint2 = Paint()
        paint2!!.xfermode = null
    }

    override fun onDraw(canvas: Canvas) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas1 = Canvas(bitmap)
        super.onDraw(canvas1)

        // 分别裁剪4个角
        drawLeftUp(canvas1)
        drawLeftDown(canvas1)
        drawRightUp(canvas1)
        drawRightDown(canvas1)
        canvas.drawBitmap(bitmap, 0f, 0f, paint2)
        bitmap.recycle()
    }

    private fun drawLeftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, roundHeight.toFloat())
        path.lineTo(0f, 0f)
        path.lineTo(roundWidth.toFloat(), 0f)
        path.arcTo(RectF(0f, 0f, (roundWidth * 2).toFloat(), (roundHeight * 2).toFloat()), -90f, -90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawLeftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, height - roundHeight.toFloat())
        path.lineTo(0f, height.toFloat())
        path.lineTo(roundWidth.toFloat(), height.toFloat())
        path.arcTo(
            RectF(
                0f,
                (height - roundHeight * 2).toFloat(),
                (roundWidth * 2).toFloat(),
                height.toFloat()
            ), 90f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(width - roundWidth.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height - roundHeight.toFloat())
        path.arcTo(
            RectF(
                (width - roundWidth * 2).toFloat(),
                (height - roundHeight * 2).toFloat(),
                width.toFloat(),
                height.toFloat()
            ), -0f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), roundHeight.toFloat())
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width - roundWidth.toFloat(), 0f)
        path.arcTo(
            RectF(
                (width - roundWidth * 2).toFloat(),
                0f,
                width.toFloat(),
                (0 + roundHeight * 2).toFloat()
            ),
            -90f,
            90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    companion object {
        // 默认的 圆角图片的圆角
        private const val cornerSize = 10
    }
}