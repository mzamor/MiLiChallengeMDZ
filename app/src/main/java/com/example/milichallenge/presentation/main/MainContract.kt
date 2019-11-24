package com.example.milachallenge.presentation.main

import android.view.View
import com.example.milachallenge.presentation.main.adapter.model.Product

interface MainContract {
    interface MainView {
        fun navigateToItemDetails(view: View, product: Product)
        fun showProductList(result: List<Product>)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface MainPresenter {
        fun attachView(mainView: MainContract.MainView)
        fun dettachView()
        fun isViewAttached(): Boolean
        fun queryProducts(site: String, productSearch: String, pagingNumber: Int)
    }
}