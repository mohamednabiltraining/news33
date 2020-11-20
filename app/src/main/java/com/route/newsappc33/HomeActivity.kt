package com.route.newsappc33

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.route.newsappc33.adapter.NewsAdapter
import com.route.newsappc33.api.ApiManager
import com.route.newsappc33.api.model.NewsResponse
import com.route.newsappc33.api.model.Source
import com.route.newsappc33.api.model.SourcesResponse
import com.route.notesappc33gsun.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() {
    val newsAdapter = NewsAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recycler_view.adapter =newsAdapter
        getSources()
    }
    fun getSources(){
        ApiManager.getApis()
            .getSources()
            .enqueue(object :Callback<SourcesResponse>{
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    showMessage(message =t.localizedMessage,
                        posActionName = getString(R.string.ok),
                        posAction = DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    if (response.isSuccessful&&
                        response.body()?.status.equals("ok")){
                        showTabs(response.body()?.sources)
                    }else {
                        showMessage(message =response.body()?.message?:"",
                            posActionName = getString(R.string.ok),
                            posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            }
                        )
                    }
                }
            })
    }

    private fun showTabs(sources: List<Source?>?) {

        sources?.forEach {item->
            val tab =  tab_layout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            tab_layout.addTab(tab)
        }

        tab_layout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source?
                    getNews(source?.id)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // call api to get news
                    val source = tab?.tag as Source?
                    getNews(source?.id)
                }
            }
        )
        tab_layout.getTabAt(0)?.select()
    }

    private fun getNews(sourceId: String?) {
        ApiManager.getApis()
            .getNews(sourceId!!)
            .enqueue(object :Callback<NewsResponse>{
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showMessage(message =t.localizedMessage,
                        posActionName = getString(R.string.ok),
                        posAction = DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        }
                    )                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progress_bar.visibility = View.GONE
                    if (response.isSuccessful&&
                        response.body()?.status.equals("ok")){
                        val list = response.body()?.articles
                        newsAdapter.changeData(list)
                    }else {
                        showMessage(message =response.body()?.message?:"",
                            posActionName = getString(R.string.ok),
                            posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            }
                        )
                    }                }
            })

    }
}