package com.route.newsappc33.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.route.newsappc33.R
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
    //val viewModel: HomeViewModel by viewModels()
    lateinit var  viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

//        val viewModel: HomeViewModel by viewModels()

        recycler_view.adapter =newsAdapter // handle view

        viewModel.getSources()

        observeLiveData()
        //Listen - subscribe on live data


    }
    fun observeLiveData(){
        viewModel.sourcesLiveData.observe(this,
            Observer {data->
                showTabs(data)// handle view
            })

        viewModel.newsListMutableLiveData.observe(this,
        Observer {
            newsAdapter.changeData(it);
        })
        viewModel.messageLiveData.observe(this, Observer {
            showMessage(message =it,
                posActionName = getString(R.string.ok),
                posAction = DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                }
            )
        })
        viewModel.messageLiveData.observe(this, Observer {
         Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })
        viewModel.showProgressBar.observe(this, Observer {
            progress_bar.visibility = View.GONE
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
                    viewModel.getNews(source?.id)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // call api to get news
                    val source = tab?.tag as Source?
                    viewModel.getNews(source?.id)
                }
            }
        )
        tab_layout.getTabAt(0)?.select()
    }


}