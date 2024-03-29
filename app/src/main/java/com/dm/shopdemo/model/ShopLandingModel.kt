package com.dm.shopdemo.model

import com.dm.shopdemo.ui.BaseFragment
import com.dm.shopdemo.ui.ShoppingLandingFragment

class ShopLandingModel : BaseModel() {

    var list: MutableList<ShopData>? = null
    override fun viewInstance(): BaseFragment? {
        return ShoppingLandingFragment.newInstance(this)
    }

    fun getListByCategories(catergoryName: String): MutableList<ShopData>? {
        val sortedList = mutableListOf<ShopData>()
        list?.forEach { sd ->
            if (sd.category.equals(catergoryName)) {
                sortedList.add(sd)
            }
        }
        return sortedList
    }

}