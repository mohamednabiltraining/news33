package com.route.newsappc33.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("imageURL")
fun loadImage(image:ImageView,url:String){
    Glide.with(image)
        .load(url)
        .into(image)
}