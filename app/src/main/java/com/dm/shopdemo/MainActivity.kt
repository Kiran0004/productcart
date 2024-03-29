package com.dm.shopdemo


import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*

import com.dm.shopdemo.model.BaseModel
import com.dm.shopdemo.networkre.EndPoints
import com.dm.shopdemo.networkre.NetworkRequest
import com.dm.shopdemo.networkre.ResponseView
import com.dm.shopdemo.networkre.presenter.BasePresenter
import com.dm.shopdemo.networkre.presenter.ShopLandingPresenter
import com.dm.shopdemo.utils.CommonUtils
import com.dm.shopdemo.ui.ShoppingCartDetailFragment
import com.dm.shopdemo.ui.ShoppingLandingFragment
import com.dm.shopdemo.ui.WishListDetailFragment


class MainActivity : AppCompatActivity(), ResponseView {

    var fragmentContainer: RelativeLayout? = null
    var networkRequest: NetworkRequest? = null
    var basePresenter: BasePresenter? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_bar)

        initNetwork()
        initialRequest()
        fragmentContainer = findViewById(R.id.fragmentContainer)
    }

    override fun addFragment(baseModel: BaseModel) {
        when {
            baseModel.actiontype == CommonUtils.ADD -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, baseModel.viewInstance()!!)
                    .addToBackStack(baseModel.pType).commit()
            }
            baseModel.actiontype == CommonUtils.REPLACE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, baseModel.viewInstance()!!)
                    .addToBackStack(baseModel.pType).commit()
            }
        }
    }

    override fun replaceFragment(baseModel: BaseModel) {
        when {
            baseModel.actiontype == CommonUtils.ADD -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, baseModel.viewInstance()!!)
                    .addToBackStack(baseModel.pType).commit()
            }
            baseModel.actiontype == CommonUtils.REPLACE -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, baseModel.viewInstance()!!)
                    .addToBackStack(baseModel.pType).commit()
            }
        }

    }

    override fun showDialogBox(text: String, flag: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initialRequest() {
        progressBar!!.setVisibility(View.VISIBLE)
        basePresenter?.executeRequest("", EndPoints.PRODCUTS,progressBar!!)
    }

    fun initNetwork() {
        networkRequest = NetworkRequest()
        basePresenter = ShopLandingPresenter(this, networkRequest!!)
    }

    override fun onBackPressed() {

        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainer)
        if(currentFragment is ShoppingLandingFragment){
                finish()


        }else if(currentFragment is ShoppingCartDetailFragment || currentFragment is WishListDetailFragment){
            super.onBackPressed()
        }
    }

}
