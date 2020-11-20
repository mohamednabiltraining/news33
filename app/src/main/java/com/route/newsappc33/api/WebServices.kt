package com.route.newsappc33.api

import com.route.newsappc33.api.model.NewsResponse
import com.route.newsappc33.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServices {

    @GET("sources")
   fun getSources():Call<SourcesResponse>

    @GET("everything")
    fun getNews(@Query("sources") sources:String)
    :Call<NewsResponse>
}