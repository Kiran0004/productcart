package com.dm.shopdemo.utils

import android.app.ProgressDialog
import com.dm.shopdemo.model.ShopData

class CommonUtils {
    companion object {
        var ADD="add";
        var REPLACE="replace"
        var CART_SUCCESS  = "Your product successfully added to the cart"
        var CART_REMOVE = "Your product successfully removed from the cart"
        var  wishListData =  mutableListOf<ShopData>()

    }
}