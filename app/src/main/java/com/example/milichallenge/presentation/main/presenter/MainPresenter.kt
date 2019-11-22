package com.example.milachallenge.presentation.main.presenter

import android.util.Log
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductInteractor
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductInteractorImpl
import com.example.milichallenge.presentation.service.SearchServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainPresenter(searchProductInteractor : SearchProductInteractor) : MainContract.MainPresenter {
    var view: MainContract.MainView? = null
    var searchProductInteractor : SearchProductInteractor? = null

    init {
        this.searchProductInteractor = searchProductInteractor
    }

    override fun queryProducts(site: String, productSearch: String, pagingNumber : Int) {
       this.searchProductInteractor?.queryProducts(site,productSearch,pagingNumber,object : SearchProductInteractor.SearchProductsCallback{
           override fun onSearchProductsSuccess(resultSearch: ResultSearch) {
               if(isViewAttached()) {
                   view?.hideProgressBar()
                   view?.showProductList(resultSearch)
               }
           }

           override fun onSearchProductsFailure(errorMsg: String) {
               if(isViewAttached()) {
                   view?.hideProgressBar()
               }
           }

       })
    }

    override fun dettachView() {
        view = null
    }

    override fun attachView(mainView: MainContract.MainView) {
        view = mainView
    }

    override fun isViewAttached() : Boolean {
        return view != null
    }

}