package com.route.newsappc33.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.route.newsappc33.R
import com.route.newsappc33.api.model.NewsItem
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter (var newsList:List<NewsItem?>?) :RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news,parent,false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = newsList?.size ?:0


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList?.get(position)

        holder.title.setText(news?.title)
        holder.desc.setText(news?.description)
        //Todo: change date format to dd/MM/yyyy hh:mm
        holder.date.setText(news?.publishedAt)
//        Glide.with(holder.itemView)
//            .load(news?.urlToImage)
//            .into(holder.image)
        holder.image.loadImage(news?.urlToImage)
    }
    fun changeData(newsList:List<NewsItem?>?){
        this.newsList=newsList
        notifyDataSetChanged()
    }

    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val title = itemView.title
        val date = itemView.date
        val desc = itemView.desc
        val image = itemView.image
    }
}

fun ImageView.loadImage(url:String?){
    Glide.with(this)
        .load(url)
        .into(this)
}