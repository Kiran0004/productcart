package com.dm.shopdemo.model

import com.dm.shopdemo.ui.BaseFragment
import com.dm.shopdemo.ui.ShoppingCartDetailFragment
import com.dm.shopdemo.ui.ShoppingLandingFragment
import java.util.*

class ShopCartDetailsModel : BaseModel() {

    var details: ShopData? = null
    //var categorylist: LinkedList<String>? = null


    override fun viewInstance(): BaseFragment? {
        return ShoppingCartDetailFragment.newInstance(this)
    }


}