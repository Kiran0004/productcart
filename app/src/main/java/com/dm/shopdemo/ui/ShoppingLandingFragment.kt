package com.dm.shopdemo.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dm.shopdemo.R
import com.dm.shopdemo.model.ShopLandingModel
import com.dm.shopdemo.ui.adapters.ShoppingLandingAdapter

import com.dm.shopdemo.MainActivity


class ShoppingLandingFragment : BaseFragment() {

    var recyclerview: RecyclerView? = null
    var shopLandingModel: ShopLandingModel? = null
    var toolbar: Toolbar? = null
    var category = arrayOf("All","Women's Footwear", "Men's Footwear", "Women's Casualwear", "Men's Casualwear","Men's Formalwear","Women's Formalwear")
    var productCategory: String? = "All"

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

        toolbar =  view.findViewById(R.id.toolbar);
        (activity as MainActivity).getDelegate().setSupportActionBar(toolbar)
        toolbar!!.setTitle("Filter Products By: ")
        val spinnerAdapter: SpinnerAdapter = ArrayAdapter(activity as MainActivity , R.layout.spinner_dropdown_item, R.id.text1, category)
        var navigationSpinner = Spinner((activity as MainActivity).getDelegate().supportActionBar!!.themedContext)
        navigationSpinner.adapter = spinnerAdapter
        toolbar!!.addView(navigationSpinner,0)
        navigationSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                   // shopLandingModel = shopLandingModel.getListByCategories(productCategory)
                    productCategory = category[0]
                    setAdapter()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    productCategory = category[position]
                    setAdapter()

            }

        }

        recyclerview?.layoutManager = LinearLayoutManager(context)
        setAdapter()
    }

    override fun getLayout() = R.layout.shopping_landing_layout

    private fun setAdapter() {
        val adapter = ShoppingLandingAdapter(context, shopLandingModel,productCategory!!)
        recyclerview?.adapter = adapter
    }
}