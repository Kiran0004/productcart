package com.dm.shopdemo.ui.adapters


import android.content.Context
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
import com.dm.shopdemo.model.BaseModel
import com.dm.shopdemo.model.ShopCartDetailsModel
import com.dm.shopdemo.model.ShopData
import com.dm.shopdemo.model.ShopLandingModel
import com.dm.shopdemo.networkre.ResponseView
import com.dm.shopdemo.ui.ShoppingLandingFragment

class ShoppingLandingAdapter(var context: Context?, shopLandingModel: ShopLandingModel?,productCategory: String,flag:Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),ResponseView {
    var list = mutableListOf<ShopData>()
    init {
        if(flag){
            list = shopLandingModel?.getProductById(3)!!
        }else{
            if (productCategory =="All") {
                list = shopLandingModel?.list!!
            }else {
                if(!flag)
                    list = shopLandingModel?.getListByCategories(productCategory)!!
            }
        }

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
                    Toast.makeText(context,"Wishlist Added successfully!!",Toast.LENGTH_SHORT).show()
                    val fragmentManager = (context as MainActivity).supportFragmentManager
                    val currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainer)
                    if(currentFragment is ShoppingLandingFragment){
                        currentFragment.AddDataToWishList("2")
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

    override fun replaceFragment(baseModel: BaseModel) {

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