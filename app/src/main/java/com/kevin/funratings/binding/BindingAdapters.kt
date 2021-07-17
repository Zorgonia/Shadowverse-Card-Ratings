package com.kevin.funratings.binding

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("imageToLoad")
    fun setImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("layoutMarginTop")
    fun setTopMargin(view: View, topMargin: Float) {
        val temp = view.layoutParams as ViewGroup.MarginLayoutParams
        temp.setMargins(temp.leftMargin, topMargin.toInt(), temp.rightMargin, temp.bottomMargin)
        view.layoutParams = temp
    }

    @JvmStatic
    @BindingAdapter("android:layout_height")
    fun setHeight(view: View, height: Float) {
        val changeParams = view.layoutParams
        changeParams.height = height.toInt()
        view.layoutParams = changeParams

    }

    @JvmStatic
    @BindingAdapter("ratingTextSetup")
    fun setHeight(view: TextView, rating: Int) {
        view.text = (rating.toFloat() /2).toString()
    }
}