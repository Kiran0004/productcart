package com.dm.shopdemo.ui.adapters


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dm.shopdemo.MainActivity
import com.dm.shopdemo.R
import com.dm.shopdemo.networkre.ResponseView

import com.google.gson.Gson

import android.content.SharedPreferences
import com.dm.shopdemo.model.*
import com.dm.shopdemo.utils.CommonUtils
import java.lang.Exception


class ShoppingLandingAdapter(var context: Context?, shopLandingModel: ShopLandingModel?,productCategory: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),ResponseView {
    var list = mutableListOf<ShopData>()

    var mPrefs: SharedPreferences? = null
    var sss: ShopLandingModel? = null
    var proId: Int = 1
    init {
        mPrefs = context!!.getSharedPreferences("MyObject",MODE_PRIVATE)
        sss = shopLandingModel
        if (productCategory =="All") {
                list = shopLandingModel?.list!!
        }else {
               list = shopLandingModel?.getListByCategories(productCategory)!!
            }
    }


    override fun replaceFragment(baseModel: BaseModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShopHolder(
            LayoutInflater.from(context).inflate(
                R.layout.shop_landing_inflate_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShopHolder) {
            val sd = list[position]
            holder.title.text = sd.name
            holder.category.text = sd.category
            proId = sd!!.productId!!
            holder.price.text = "£"+Integer.toString(sd.price!!)
            if (sd.stock!! <= 0) {
                holder.outOfStock.text = context?.resources?.getString(R.string.outofstock)
                holder.addtocartView.visibility=View.GONE
                holder.outOfStock.visibility=View.VISIBLE
                holder.wishList.visibility = View.GONE
            } else {
                holder.outOfStock.text = ""
                holder.addtocartView.visibility=View.VISIBLE
                holder.outOfStock.visibility=View.GONE
                holder.addtocartView.setOnClickListener(View.OnClickListener {
                    var detailModel: ShopCartDetailsModel? = ShopCartDetailsModel()
                    detailModel!!.details = sd
                    addFragment(detailModel)
                })
                holder.wishList.visibility = View.VISIBLE
                holder.wishList.setOnClickListener(View.OnClickListener {
                    try {
                        if(CommonUtils.wishListData!!.size>0){
                            CommonUtils.wishListData?.forEach { ssd ->
                                if (sd.name.equals(ssd.name)) {
                                    Toast.makeText(context,"Wishlist Already Added for this product!!",Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context,"Wishlist Added successfully!!",Toast.LENGTH_SHORT).show()
                                    val prefsEditor = mPrefs!!.edit()
                                    CommonUtils.wishListData.add(sd)
                                    val gson = Gson()
                                    val json = gson.toJson(CommonUtils.wishListData)
                                    prefsEditor.putString("MyObject", json)
                                    prefsEditor.commit()
                                }

                            }

                        }else{
                            Toast.makeText(context,"Wishlist Added successfully!!",Toast.LENGTH_SHORT).show()
                            CommonUtils.wishListData.add(sd)
                            val prefsEditor = mPrefs!!.edit()
                            val gson = Gson()
                            val json = gson.toJson(CommonUtils.wishListData)
                            prefsEditor.putString("MyObject", json)
                            prefsEditor.commit()
                        }
                    }catch (e: Exception){
                        Toast.makeText(context,"Problem while adding wishlist",Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    override fun addFragment(baseModel: BaseModel) {

        (context as MainActivity).supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, baseModel.viewInstance()!!)
                .addToBackStack(baseModel.pType).commit()

    }



    override fun showDialogBox(text: String, flag: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    class ShopHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.title)
        var category = view.findViewById<TextView>(R.id.category)
        var price = view.findViewById<TextView>(R.id.price)
        var outOfStock = view.findViewById<TextView>(R.id.outOfStock)
        var addtocartView = view.findViewById<Button>(R.id.addtocart)
        var wishList  =view.findViewById<ImageView>(R.id.wishlist)
    }
}