package com.example.milichallenge.presentation.domain.interactor.searchProducts

import com.example.milichallenge.presentation.itemSelected.Model.ProductDescription

interface SearchProductDescriptionInteractor {
    interface SearchProductDescriptionCallback {
        fun onSearchProductDescriptionSuccess(resultSearch: ProductDescription)
        fun onSearchProductDescriptionFailure(errorMsg: String)
    }

    fun queryProductsDescription(
        productId: String,
        listener: SearchProductDescriptionCallback
    )

}