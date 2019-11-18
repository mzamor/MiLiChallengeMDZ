package com.example.milachallenge.presentation.main

import android.view.View
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milachallenge.presentation.main.adapter.model.ResultSearch

interface MainContract {
    interface MainView{
        fun navigateToItemDetails(view: View, product: Product)
        fun showProductList(resultSearch:ResultSearch)
    }

    interface MainPresenter{
        fun attachView(mainView:MainContract.MainView)
        fun dettachView()
        fun queryProducts(site:String, productSearch:String)
    }
}