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
import com.dm.shopdemo.networkre.WishListInterface


class ShoppingLandingFragment : BaseFragment(), WishListInterface {

    var recyclerview: RecyclerView? = null
    var shopLandingModel: ShopLandingModel? = null
    var toolbar: Toolbar? = null
    var category = arrayOf("All","Women's Footwear", "Men's Footwear", "Women's Casualwear", "Men's Casualwear","Men's Formalwear","Women's Formalwear")
    var productCategory: String? = "All"
    var wishlist: Button? = null
    companion object {
        fun newInstance(response: Parcelable): ShoppingLandingFragment {
            return ShoppingLandingFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(mTAG, response)
                arguments = bundle
            }
        }
    }

    override fun initializeViews(view: View) {
        shopLandingModel = arguments?.getParcelable(mTAG)
        recyclerview = view.findViewById(R.id.recyclerview)
        wishlist = view.findViewById(R.id.viewWishList)
        toolbar =  view.findViewById(R.id.toolbar);
        (activity as MainActivity).getDelegate().setSupportActionBar(toolbar)
        toolbar!!.setTitle("Filter By: ")
        val spinnerAdapter: SpinnerAdapter = ArrayAdapter(activity as MainActivity , R.layout.spinner_dropdown_item, R.id.text1, category)
        var navigationSpinner = Spinner((activity as MainActivity).getDelegate().supportActionBar!!.themedContext)
        navigationSpinner.adapter = spinnerAdapter
        navigationSpinner!!.setBackgroundResource(R.drawable.selector_spinner)
        toolbar!!.addView(navigationSpinner,0)
        navigationSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                    productCategory = category[0]
                    setAdapter(false)
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    productCategory = category[position]
                    setAdapter(false)

            }

        }

        wishlist!!.setOnClickListener(View.OnClickListener {
            productCategory = category[0]
            setAdapter(true)
        })

        recyclerview?.layoutManager = LinearLayoutManager(context)
        setAdapter(false)
    }

    override fun getLayout() = R.layout.shopping_landing_layout

    override fun AddDataToWishList(list: String) {

    }

    private fun setAdapter(flag: Boolean) {
        val adapter = ShoppingLandingAdapter(context, shopLandingModel,productCategory!!,flag)
        recyclerview?.adapter = adapter
    }
}