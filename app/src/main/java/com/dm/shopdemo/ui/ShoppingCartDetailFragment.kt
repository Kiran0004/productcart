package com.dm.shopdemo.ui

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.Window
import android.widget.*

import com.dm.shopdemo.R

import com.dm.shopdemo.model.BaseModel
import com.dm.shopdemo.model.ShopCartDetailsModel
import com.dm.shopdemo.networkre.EndPoints
import com.dm.shopdemo.networkre.NetworkRequest
import com.dm.shopdemo.networkre.ResponseView
import com.dm.shopdemo.networkre.presenter.ShopLandingPresenter


class ShoppingCartDetailFragment : BaseFragment(), ResponseView {


    var shopCartDetailsModel: ShopCartDetailsModel? = null
    var title: TextView? = null
    var categoryView: TextView? = null
    var priceView: TextView? = null
    var imageView: ImageView? = null
    var networkRequest: NetworkRequest? = null
    var progressBar: ProgressBar? = null
    var addToCart: Button? = null
    companion object {
        fun newInstance(response: Parcelable): ShoppingCartDetailFragment {
            return ShoppingCartDetailFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(mTAG, response)
                arguments = bundle
            }
        }
    }

    override fun initializeViews(view: View) {
        shopCartDetailsModel = arguments?.getParcelable(mTAG)
        title = view.findViewById(R.id.title)
        progressBar = view.findViewById(R.id.progress_bar)
        categoryView = view.findViewById(R.id.category1)
        imageView = view.findViewById(R.id.imageView)
        priceView = view.findViewById(R.id.price1)
        addToCart = view.findViewById(R.id.addtocart)
        title!!.text = shopCartDetailsModel!!.details!!.name
        categoryView!!.text = shopCartDetailsModel!!.details!!.category
        priceView!!.text = " £"+ shopCartDetailsModel!!.details!!.price
        if(shopCartDetailsModel!!.details!!.category!!.contains("Footwear"))
            imageView!!.setImageDrawable(resources.getDrawable(R.drawable.foo))
        else
            imageView!!.setImageDrawable(resources.getDrawable(R.drawable.cloth))
        addToCart!!.setOnClickListener(View.OnClickListener {

            initNetwork()
            initialRequest(EndPoints.CART)
        })

    }

    private fun initialRequest(endPoints: String) {
        progressBar!!.setVisibility(View.VISIBLE)
        basePresenter?.executeRequest("", endPoints,progressBar!!)
    }

    fun initNetwork() {
        networkRequest = NetworkRequest()
        basePresenter = ShopLandingPresenter(this, networkRequest!!)
    }

    override fun addFragment(baseModel: BaseModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replaceFragment(baseModel: BaseModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDialogBox(result: String,flag: Boolean) {
        val dialog = Dialog(activity!!)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.custom_layout)
        val body = dialog .findViewById(R.id.tvBody) as TextView
        body.text = result
        val yesBtn = dialog .findViewById(R.id.btn_yes) as Button
        val noBtn = dialog .findViewById(R.id.btn_remove) as Button
        if(flag)
            noBtn!!.visibility = View.GONE
        else
            noBtn!!.visibility = View.VISIBLE
        yesBtn.setOnClickListener {
            dialog.dismiss()
            activity!!.onBackPressed()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
            initNetwork()
            initialRequest(EndPoints.CART_DELETE)

        }
        //  noBtn.setOnClickListener { dialog .dismiss() }
        dialog .show()

    }
    override fun getLayout() = R.layout.shop_details_layout


}