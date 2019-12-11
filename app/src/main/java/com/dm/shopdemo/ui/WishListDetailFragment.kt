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
import com.dm.shopdemo.model.WishListModel
import com.dm.shopdemo.networkre.WishListInterface
import com.dm.shopdemo.ui.adapters.WishListAdapter
import com.dm.shopdemo.utils.CommonUtils


class WishListDetailFragment : BaseFragment(), WishListInterface {

    var recyclerview: RecyclerView? = null
    var shopLandingModel: WishListModel? = null

    companion object {
        fun newInstance(response: Parcelable): WishListDetailFragment {
            return WishListDetailFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(mTAG, response)
                arguments = bundle
            }
        }
    }

    override fun initializeViews(view: View) {
        shopLandingModel = arguments?.getParcelable(mTAG)
        recyclerview = view.findViewById(R.id.recyclerview)

        recyclerview?.layoutManager = LinearLayoutManager(context)
        setAdapter(false)
    }

    override fun getLayout() = R.layout.wishlist_layout

    override fun AddDataToWishList(list: String) {

    }

    private fun setAdapter(flag: Boolean) {
        val adapter = WishListAdapter(context, shopLandingModel)
        recyclerview?.adapter = adapter
    }
}