package com.dm.shopdemo.networkre.presenter

import android.widget.ProgressBar
import com.dm.shopdemo.networkre.NetworkCallBack
import com.dm.shopdemo.networkre.NetworkRequest
import com.dm.shopdemo.networkre.ResponseView

open class BasePresenter(responseView: ResponseView, networkRequest: NetworkRequest) {

    var responseView: ResponseView? = null
    var networkRequest: NetworkRequest? = null

    init {
        this.responseView = responseView
        this.networkRequest = networkRequest
    }

    open fun executeRequest(req: String, endpoint: String,progressBar: ProgressBar) {

    }
}