package com.dm.shopdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dm.shopdemo.networkre.presenter.BasePresenter

abstract class BaseFragment : Fragment() {

    var mTAG = BaseFragment::class.java.simpleName
    var basePresenter: BasePresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
    }

     fun onBackPressed(){

    }

    abstract fun initializeViews(view: View)

    abstract fun getLayout(): Int


}