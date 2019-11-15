package com.example.milachallenge.presentation.main

interface MainContract {
    interface MainView{
        fun navigateToItemDetails()

    }

    interface MainPresenter{
        fun attachView(mainView:MainContract.MainView)
        fun dettachView()
        fun queryProducts(site:String, productSearch:String)


    }
}