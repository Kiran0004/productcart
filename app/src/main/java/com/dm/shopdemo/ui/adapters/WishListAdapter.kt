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

import com.dm.shopdemo.model.*



class WishListAdapter(var context: Context?, shopLandingModel: WishListModel?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),ResponseView {
    var list_val: List<ShopData>? = null
    var proId: Int = 1
    init {
        list_val = shopLandingModel!!.list!!
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
        return list_val!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShopHolder) {
            val sd = list_val!![position]
            holder.title.text = sd.name
            holder.category.text = sd.category
            proId = sd!!.productId!!
            holder.price.text = "£"+Integer.toString(sd.price!!)
            holder.wishList.visibility = View.GONE
            if (sd.stock!! <= 0) {
                holder.outOfStock.text = context?.resources?.getString(R.string.outofstock)
                holder.addtocartView.visibility=View.GONE
                holder.outOfStock.visibility=View.VISIBLE
            } else {
                holder.outOfStock.text = ""
                holder.addtocartView.visibility=View.VISIBLE
                holder.outOfStock.visibility=View.GONE
                holder.addtocartView.setOnClickListener(View.OnClickListener {
                    var detailModel: ShopCartDetailsModel? = ShopCartDetailsModel()
                    detailModel!!.details = sd
                    addFragment(detailModel)
                })
            }
        }
    }

    override fun addFragment(baseModel: BaseModel) {

        (context as MainActivity).supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, baseModel.viewInstance()!!)
                .addToBackStack(baseModel.pType).commit()

    }

    override fun replaceFragment(baseModel: BaseModel) {

        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, baseModel.viewInstance()!!)
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