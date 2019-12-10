package com.dm.shopdemo.networkre.presenter

import android.widget.ProgressBar
import com.dm.shopdemo.model.ShopData
import com.dm.shopdemo.model.ShopLandingModel
import com.dm.shopdemo.networkre.EndPoints
import com.dm.shopdemo.networkre.NetworkCallBack
import com.dm.shopdemo.networkre.NetworkRequest
import com.dm.shopdemo.networkre.ResponseView
import com.dm.shopdemo.utils.CommonUtils
import org.json.JSONArray
import java.util.*


class ShopLandingPresenter(var rv: ResponseView, var nr: NetworkRequest) :
    BasePresenter(rv, nr) {

    override fun executeRequest(req: String, endpoint: String,progressBar: ProgressBar) {
        nr.makeRequest(req, endpoint, object : NetworkCallBack {
            override fun onSuccess(response: Any) {
                if(endpoint.equals(EndPoints.PRODCUTS))
                    rv.addFragment(convertData(response as String, endpoint))
                else if(endpoint.equals(EndPoints.CART)){
                    rv.showDialogBox(CommonUtils.CART_SUCCESS,false)
                }else{
                    rv.showDialogBox(CommonUtils.CART_REMOVE,true)
                }

            }

            override fun onFailure(e: Exception) {

            }
        },progressBar)
    }

    fun convertData(jsonString: String, endpoint: String): ShopLandingModel {
        val jsonarray = JSONArray(jsonString)
        val list = mutableListOf<ShopData>()
        val categoryList=LinkedList<String>()
        for (count in 0 until jsonarray.length()) {
            val obj = jsonarray.getJSONObject(count)
            val productId = obj.getInt("productId")
            val name = obj.getString("name")
            val category = obj.getString("category")
            val price = obj.getInt("price")
            var oldPrice = -1
            if (obj.get("oldPrice") is Integer) {
                oldPrice = obj.getInt("oldPrice")
            }
            val stock = obj.getInt("stock")
            val m = ShopData()
            m.productId = productId
            m.name = name
            m.category = category
            m.price = price
            m.oldPrice = oldPrice
            m.stock = stock
            categoryList.add(category)
            list.add(m)
        }
        val landingModel = ShopLandingModel()
        landingModel.list = list
        landingModel.pType = endpoint
        landingModel.actiontype=CommonUtils.ADD
        return landingModel
    }

}