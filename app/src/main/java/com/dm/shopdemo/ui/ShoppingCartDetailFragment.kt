package com.dm.shopdemo.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dm.shopdemo.R
import com.dm.shopdemo.model.ShopLandingModel
import com.dm.shopdemo.ui.adapters.ShoppingLandingAdapter

import com.dm.shopdemo.MainActivity
import com.dm.shopdemo.model.ShopCartDetailsModel


class ShoppingCartDetailFragment : BaseFragment() {


    var shopCartDetailsModel: ShopCartDetailsModel? = null
    var title: TextView? = null
    var categoryView: TextView? = null
    var priceView: TextView? = null
  //  var toolbar: Toolbar? = null
    //var category = arrayOf("All","Women's Footwear", "Men's Footwear", "Women's Casualwear", "Men's Casualwear","Men's Formalwear","Women's Formalwear")
    //var productCategory: String? = "All"

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
        categoryView = view.findViewById(R.id.category)
        priceView = view.findViewById(R.id.price)
        title!!.text = "Product         :"+shopCartDetailsModel!!.details!!.name
        categoryView!!.text = "Category         :"+shopCartDetailsModel!!.details!!.category
        priceView!!.text = "Price           : £"+ shopCartDetailsModel!!.details!!.price


    }

    override fun getLayout() = R.layout.shop_details_layout


}