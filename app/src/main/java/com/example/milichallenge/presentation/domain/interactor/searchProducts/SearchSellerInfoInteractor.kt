package com.example.milichallenge.presentation.domain.interactor.searchProducts

import com.example.milichallenge.presentation.itemSelected.Model.SellerData

interface SearchSellerInfoInteractor {
    interface SearchSellerInfoCallback {
        fun onSearchSellerInfoSuccess(resultSearch: SellerData)
        fun onSearchSellerInfoFailure(errorMsg: String)
    }

    fun queryProducts(
        site: String,
        sellerId: String,
        listener: SearchSellerInfoCallback
    )

}