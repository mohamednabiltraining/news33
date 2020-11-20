package com.route.newsappc33.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.route.newsappc33.R
import com.route.newsappc33.adapter.NewsAdapter
import com.route.newsappc33.api.model.Source
import com.route.newsappc33.databinding.ActivityHomeBinding
import com.route.notesappc33gsun.base.BaseActivity

class HomeActivity :
    BaseActivity<ActivityHomeBinding,HomeViewModel>() {
    val newsAdapter = NewsAdapter(null)

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun createViewModel(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewDataBinding.recyclerView.adapter =newsAdapter // handle view
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
            viewDataBinding.progressBar.visibility = View.GONE
        })
    }

    private fun showTabs(sources: List<Source?>?) {

        sources?.forEach {item->
            val tab =  viewDataBinding.tabLayout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            viewDataBinding.tabLayout.addTab(tab)
        }

        viewDataBinding.tabLayout.addOnTabSelectedListener(
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
        viewDataBinding.tabLayout.getTabAt(0)?.select()
    }


}