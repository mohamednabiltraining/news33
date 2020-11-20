package com.route.newsappc33.ui.home

import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.route.newsappc33.R
import com.route.newsappc33.api.ApiManager
import com.route.newsappc33.api.model.NewsItem
import com.route.newsappc33.api.model.NewsResponse
import com.route.newsappc33.api.model.Source
import com.route.newsappc33.api.model.SourcesResponse
import com.route.newsappc33.utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel :ViewModel(){
    // handle Logic - Hold Data

    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsListMutableLiveData = MutableLiveData<List<NewsItem?>?>()
    val messageLiveData = MutableLiveData<String>()
    val showProgressBar = MutableLiveData<Boolean>()
    fun getSources(){
        ApiManager.getApis()
            .getSources()
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    messageLiveData.value = t.localizedMessage;
//                    showMessage(message =t.localizedMessage,
//                        posActionName = getString(R.string.ok),
//                        posAction = DialogInterface.OnClickListener { dialog, which ->
//                            dialog.dismiss()
//                        }
//                    )
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    if (response.isSuccessful&&
                        response.body()?.status.equals("ok")){
                        //showTabs(response.body()?.sources)
                        // data is exist -> put in live data
                        sourcesLiveData.value = response.body()?.sources;
                    }else {
                        messageLiveData.value = response.body()?.message?:"";
                    }
                }
            })
    }
    fun getNews(sourceId: String?) {
        ApiManager.getApis()
            .getNews(sourceId!!)
            .enqueue(object :Callback<NewsResponse>{
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    messageLiveData.value = t.localizedMessage;

                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {

                    showProgressBar.value=false
                   // progress_bar.visibility = View.GONE
                    if (response.isSuccessful&&
                        response.body()?.status.equals("ok")){
                        val list = response.body()?.articles
                     //   newsAdapter.changeData(list)
                   //     newsListMutableLiveData.postValue(list) // thread safety
                        newsListMutableLiveData.value = list
                    }else {
                        messageLiveData.value = response.body()?.message?:"";
                    }
                }
            })

    }
}