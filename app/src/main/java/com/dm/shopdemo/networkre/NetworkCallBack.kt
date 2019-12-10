package com.dm.shopdemo.networkre

import android.icu.lang.UCharacter.GraphemeClusterBreak.T


interface NetworkCallBack {
    fun onSuccess(response: Any)
    fun onFailure(e: Exception)
}