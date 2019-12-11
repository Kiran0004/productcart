package com.dm.shopdemo.ui

import android.content.Context
import android.content.SharedPreferences
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
import com.dm.shopdemo.model.BaseModel
import com.dm.shopdemo.model.ShopData
import com.dm.shopdemo.model.WishListModel
import com.dm.shopdemo.networkre.WishListInterface
import com.dm.shopdemo.utils.CommonUtils
import com.google.gson.Gson
import org.json.JSONArray
import java.util.*


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
               // CommonUtils.wishListFlag = false
                    setAdapter(false)
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    productCategory = category[position]
              //  CommonUtils.wishListFlag = false
                    setAdapter(false)

            }

        }

        wishlist!!.setOnClickListener(View.OnClickListener {
            var list = mutableListOf<ShopData>()

            var mPrefs: SharedPreferences? = null
            var sss: ShopLandingModel? = null
            var proId: Int = 1

            mPrefs = context!!.getSharedPreferences("MyObject", Context.MODE_PRIVATE)
            sss = shopLandingModel
           // if(flag){
                val gson1 = Gson()
                val json1 = mPrefs!!.getString("MyObject", "")

                //  val type = object : TypeToken<MutableList<ShopData>>() {}.type



                retrieveWishListData(json1!!)


            //}
        })

        recyclerview?.layoutManager = LinearLayoutManager(context)
        setAdapter(false)
    }

    override fun getLayout() = R.layout.shopping_landing_layout

    override fun AddDataToWishList(list: String) {

    }

    private fun setAdapter(flag: Boolean) {
        val adapter = ShoppingLandingAdapter(context, shopLandingModel,productCategory!!)
        recyclerview?.adapter = adapter
    }


    fun retrieveWishListData(jsonString: String): MutableList<ShopData> {
        val jsonarray = JSONArray(jsonString)
        val list_val = mutableListOf<ShopData>()
        val categoryList= LinkedList<String>()
        for (count in 0 until jsonarray.length()) {
            val obj = jsonarray.getJSONObject(count)
            val productId = obj.getInt("productId")
            val name = obj.getString("name")
            val category = obj.getString("category")
            val price = obj.getInt("price")
            var oldPrice = -1
            val stock = obj.getInt("stock")
            val m = ShopData()
            m.productId = productId
            m.name = name
            m.category = category
            m.price = price
            m.oldPrice = oldPrice
            m.stock = stock
            categoryList.add(category)
            list_val.add(m)

        }
        var detailModel1: WishListModel? = WishListModel()
        detailModel1!!.list = list_val!!
        CommonUtils.wishListData = list_val!!
        // detailModel = sd!!
        replaceFragment(detailModel1)
        return list_val
    }
     fun replaceFragment(baseModel: BaseModel) {

        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, baseModel.viewInstance()!!)
            .addToBackStack(baseModel.pType).commit()

    }
}