package com.dm.shopdemo.networkre

import com.dm.shopdemo.model.BaseModel

interface ResponseView {
    fun addFragment(baseModel: BaseModel)
    fun replaceFragment(baseModel: BaseModel)
    fun showDialogBox(text: String,flag: Boolean)
}