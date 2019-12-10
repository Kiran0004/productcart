package com.dm.shopdemo.model

import android.os.Parcel
import android.os.Parcelable
import com.dm.shopdemo.ui.BaseFragment

open class BaseModel() : Parcelable {

    var actiontype: String? = null
    var pType: String? = null

    constructor(parcel: Parcel) : this() {
        actiontype = parcel.readString()
    }

    open fun viewInstance(): BaseFragment? {
        return null
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(actiontype)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseModel> {
        override fun createFromParcel(parcel: Parcel): BaseModel {
            return BaseModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseModel?> {
            return arrayOfNulls(size)
        }
    }

}