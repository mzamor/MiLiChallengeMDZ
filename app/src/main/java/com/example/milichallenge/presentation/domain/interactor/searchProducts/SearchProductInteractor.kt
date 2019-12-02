package com.example.milichallenge.presentation.domain.interactor.searchProducts

import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milachallenge.presentation.main.adapter.model.ResultSearch

interface SearchProductInteractor {
    interface SearchProductsCallback {
        fun onSearchProductsSuccess(resultProducts: List<Product>)
        fun onSearchProductsFailure(errorMsg: String)
    }

    fun queryProducts(
        site: String,
        productSearch: String,
        pagingNumber: Int,
        listener: SearchProductsCallback
    )

}