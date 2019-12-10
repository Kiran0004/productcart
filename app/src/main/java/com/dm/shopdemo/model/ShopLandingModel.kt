package com.dm.shopdemo.model

import com.dm.shopdemo.ui.BaseFragment
import com.dm.shopdemo.ui.ShoppingLandingFragment
import java.util.*

class ShopLandingModel : BaseModel() {

    var list: MutableList<ShopData>? = null
    var categorylist: LinkedList<String>? = null


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

    fun getProductById(id: Int): ShopData? {
        list?.forEach { sd ->
            if (sd.productId == id) {
                return sd
            }
        }
        return null
    }

}