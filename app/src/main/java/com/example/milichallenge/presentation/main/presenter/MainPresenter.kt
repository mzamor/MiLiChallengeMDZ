package com.example.milachallenge.presentation.main.presenter

import com.example.milachallenge.presentation.main.MainContract

class MainPresenter : MainContract.MainPresenter {
    var view: MainContract.MainView? = null


    override fun dettachView() {
        view = null
    }

    override fun attachView(mainView: MainContract.MainView) {
        view = mainView
    }

    override fun searchProducts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}