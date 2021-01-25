package cn.ccwb.lib_base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import cn.ccwb.lib_base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.supercharge.shimmerlayout.ShimmerLayout

/**
 * Glide Utils
 */
class GlideUtils {

    companion object {

        fun loadNetPic(path: String?, context: Context, targetView: ImageView,resId: Int) {
            Glide.with(context).load(path)
                .placeholder(resId)
                .error(resId)
                .into(targetView)
        }

        fun loadRes(resId: Int, context: Context, targetView: ImageView) {
            Glide.with(context)
                .load(resId)
                .into(targetView)
        }


        fun loadResWithoutContext(resId: Int, view: View, targetView: ImageView) {
            Glide.with(view)
                .load(resId)
                .into(targetView)
        }

        fun loadPic(imagePath: String?, view: View, targetView: ImageView, resId: Int) {
            Glide.with(view)
                .load(imagePath)
                .placeholder(resId)
                .error(resId)
                .into(targetView)
        }

        fun loadPicWithShimmer(imagePath: String?, view: View, targetView: ImageView, resId: Int, shimmer: ShimmerLayout) {
            Glide.with(view)
                .load(imagePath)
                .placeholder(resId)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false.also { shimmer?.stopShimmerAnimation() }
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false.also { shimmer?.stopShimmerAnimation() }
                    }
                })
                .into(targetView)
        }


    }


}