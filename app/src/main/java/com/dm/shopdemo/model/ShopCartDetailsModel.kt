package com.dm.shopdemo.model

import com.dm.shopdemo.ui.BaseFragment
import com.dm.shopdemo.ui.ShoppingCartDetailFragment

class ShopCartDetailsModel : BaseModel() {

    var details: ShopData? = null
    override fun viewInstance(): BaseFragment? {
        return ShoppingCartDetailFragment.newInstance(this)
    }


}