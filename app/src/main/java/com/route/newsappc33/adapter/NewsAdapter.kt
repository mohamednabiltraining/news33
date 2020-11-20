package com.route.newsappc33.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.route.newsappc33.R
import com.route.newsappc33.api.model.NewsItem
import com.route.newsappc33.databinding.ItemNewsBinding
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter (var newsList:List<NewsItem?>?) :RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_news,parent,false);
        val viewBinding = DataBindingUtil.inflate<ItemNewsBinding>(
            LayoutInflater.from(parent.context),R.layout.item_news,parent,
            false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = newsList?.size ?:0


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList?.get(position)
        holder.bind(news)
    }
    fun changeData(newsList:List<NewsItem?>?){
        this.newsList=newsList
        notifyDataSetChanged()
    }

    class ViewHolder (val viewBinding: ItemNewsBinding)
        :RecyclerView.ViewHolder(viewBinding.root){
       fun bind(news:NewsItem?){
           viewBinding.news = news;
           viewBinding.executePendingBindings()
       }
    }
}

fun ImageView.loadImage(url:String?){
    Glide.with(this)
        .load(url)
        .into(this)
}