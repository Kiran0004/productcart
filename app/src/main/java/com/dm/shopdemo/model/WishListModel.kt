package com.dm.shopdemo.model

import com.dm.shopdemo.ui.BaseFragment
import com.dm.shopdemo.ui.ShoppingLandingFragment
import com.dm.shopdemo.ui.WishListDetailFragment

class WishListModel : BaseModel() {

    var list: List<ShopData>? = null
    override fun viewInstance(): BaseFragment? {
        return WishListDetailFragment.newInstance(this)
    }

    fun getListByCategories(catergoryName: String): List<ShopData>? {
        val sortedList = mutableListOf<ShopData>()
        list?.forEach { sd ->
            if (sd.category.equals(catergoryName)) {
                sortedList.add(sd)
            }
        }
        return sortedList
    }

}