package cn.ccwb.lib_base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.coorchice.library.ImageEngine
import com.coorchice.library.image_engine.Engine

/**
 * SuperTextView 加载图片的 Engine
 */
class SuperTextViewGlideEngine(private var context: Context) : Engine {

    @SuppressLint("CheckResult")
    override fun load(url: String?, callback: ImageEngine.Callback?) {
        Glide.with(context).load(url).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false.also { callback?.onCompleted(resource) }
            }

        })
    }
}