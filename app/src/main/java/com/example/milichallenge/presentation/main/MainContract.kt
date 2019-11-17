package com.example.milachallenge.presentation.main

import com.example.milachallenge.presentation.main.adapter.model.ResultSearch

interface MainContract {
    interface MainView{
        fun navigateToItemDetails()
        fun showProductList(resultSearch:ResultSearch)
    }

    interface MainPresenter{
        fun attachView(mainView:MainContract.MainView)
        fun dettachView()
        fun queryProducts(site:String, productSearch:String)
    }
}