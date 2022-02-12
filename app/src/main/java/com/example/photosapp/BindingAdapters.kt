package com.example.photosapp

import android.view.View
import android.webkit.WebSettings
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.model.GlideUrl
import com.example.domain.ApiStatus
import com.example.domain.Photo
import com.example.photosapp.recyclerview.adapters.PhotosAdapter
import com.example.photosapp.recyclerview.adapters.PhotosSnapAdapter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

@BindingAdapter("listDataInSnap")
fun bindRecyclerViewWithSnap(recyclerView: RecyclerView, data: List<Photo>?) {
    val adapter = recyclerView.adapter as PhotosSnapAdapter
    adapter.submitList(data)
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Photo>?) {
    val adapter = recyclerView.adapter as PhotosAdapter
    adapter.submitList(data)
}

// Use Glide to display the image
@BindingAdapter("imageUrl") // To use this * binding annotations *, 'kotlin-kapt' plugin is needed
fun bindImage(imgView: ImageView, url: String?) {
    url?.let {
        val urlForGlide = GlideUrl(
            it, LazyHeaders.Builder()
                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(imgView.context))
                .build()
        )
        Glide.with(imgView.context)
            .load(urlForGlide)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgView)
    }
}

@BindingAdapter("ApiStatus")
fun bindStatusObservables(statusImageView: ImageView, status: Observable<ApiStatus>) {
    status.subscribe(object : io.reactivex.Observer<ApiStatus> {

        override fun onSubscribe(d: Disposable) { }

        override fun onError(e: Throwable) { }

        override fun onComplete() { }

        override fun onNext(t: ApiStatus) {
            when (t) {
                ApiStatus.LOADING -> {
                    statusImageView.visibility = View.VISIBLE
                    statusImageView.setImageResource(R.drawable.loading_animation)
                    statusImageView.layoutParams.height = 600
                    statusImageView.layoutParams.width = 600
                    statusImageView.requestLayout()
                }
                ApiStatus.ERROR -> {
                    statusImageView.visibility = View.VISIBLE
                    statusImageView.setImageResource(R.drawable.ic_bx_wifi_off)
                    statusImageView.layoutParams.height = 200
                    statusImageView.layoutParams.width = 200
                    statusImageView.requestLayout()
                }
                else -> {
                    statusImageView.visibility = View.GONE
                }
            }
        }
    })

}