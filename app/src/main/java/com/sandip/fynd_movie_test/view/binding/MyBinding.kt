package com.sandip.fynd_movie_test.view.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object MyBinding {
    @BindingAdapter("movie_image")
    @JvmStatic
    fun setMovieImage(imageView: ImageView, url:String?){
        url?.let {
            Glide.with(imageView.context)
                .load("https://image.tmdb.org/t/p/w500/$url")
                .priority(Priority.HIGH)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }

    }

}