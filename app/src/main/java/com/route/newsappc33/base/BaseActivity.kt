package com.route.notesappc33gsun.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.route.newsappc33.R
import com.route.newsappc33.databinding.ActivityHomeBinding
import com.route.newsappc33.ui.home.HomeViewModel

 abstract class BaseActivity<DB :ViewDataBinding, VM: ViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: DB
    //val viewModel: HomeViewModel by viewModels()
    lateinit var  viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding =
            DataBindingUtil.setContentView(this,
                getLayoutId())
        viewModel = createViewModel();
    }
    abstract fun createViewModel():VM;
    abstract fun getLayoutId():Int

    var dialoge: AlertDialog? = null;

    fun showMessage(
        title: String? = null,
        message: String,
        posActionName: String? = null,
        posAction: DialogInterface.OnClickListener? = null,
        negActionName: String? = null,
        negAction: DialogInterface.OnClickListener? = null,
        cancelable: Boolean = true
    ) {

        dialoge = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(posActionName, posAction)
            .setNegativeButton(negActionName, negAction)
            .setCancelable(cancelable)
            .show()
    }

    fun hideDialoge() {
        dialoge?.dismiss()
    }

    var loadingDialoge :ProgressDialog? =null;
    fun showLoading(message:String){
        loadingDialoge = ProgressDialog(this);
        loadingDialoge?.setMessage(message)
        loadingDialoge?.setCancelable(false)
        loadingDialoge?.show();
    }
    fun hideLoading(){
        loadingDialoge?.dismiss()
    }
}