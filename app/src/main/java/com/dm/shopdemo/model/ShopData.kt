package com.dm.shopdemo.model


import com.dm.shopdemo.ui.BaseFragment
import com.dm.shopdemo.ui.ShoppingLandingFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class ShopData : BaseModel() {

    var productId: Int? = null
    var name: String? = null
    var category: String? = null
    var price: Int? = null
    var oldPrice: Int? = null
    var stock: Int? = null

    override fun viewInstance(): BaseFragment? {
        return ShoppingLandingFragment.newInstance(this)
    }
}