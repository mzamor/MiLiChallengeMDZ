package com.example.milachallenge.presentation.main

import com.example.milichallenge.presentation.itemSelected.Model.ProductDescription
import com.example.milichallenge.presentation.itemSelected.Model.SellerData

interface ItemDetailsContract {
    interface ItemDetailView {
        fun showSellerInfo(result: SellerData)
        fun showProductDescription(result: ProductDescription)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface ItemDetailPresenter {
        fun attachView(mainView: ItemDetailsContract.ItemDetailView)
        fun dettachView()
        fun isViewAttached(): Boolean
        fun querySellerInfo(site: String, idSeller: String)
        fun queryProductDescription(idProduct: String)
    }
}