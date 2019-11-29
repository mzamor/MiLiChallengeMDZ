package com.example.milichallenge.presentation.itemSelected.presenter

import com.example.milachallenge.presentation.main.ItemDetailsContract
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchSellerInfoInteractor
import com.example.milichallenge.presentation.itemSelected.Model.SellerData

class ItemDetailsPresenter(searchSellerInfoInteractor: SearchSellerInfoInteractor) :
    ItemDetailsContract.ItemDetailPresenter {
    var view: ItemDetailsContract.ItemDetailView? = null
    var searchSellerInfoInteractor: SearchSellerInfoInteractor? = null

    init {
        this.searchSellerInfoInteractor = searchSellerInfoInteractor
    }

    override fun attachView(itemDetailsView: ItemDetailsContract.ItemDetailView) {
        view = itemDetailsView
    }

    override fun dettachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun querySellerInfo(site: String, idSeller: String) {
        view?.showProgressBar()
        searchSellerInfoInteractor?.queryProducts(
            site,
            idSeller,
            object : SearchSellerInfoInteractor.SearchSellerInfoCallback {
                override fun onSearchSellerInfoSuccess(resultSearch: SellerData) {
                    if (isViewAttached()) {
                        view?.hideProgressBar()
                        view?.showSellerInfo(resultSearch)
                    }
                }

                override fun onSearchSellerInfoFailure(errorMsg: String) {
                    view?.hideProgressBar()
                }
            })
    }

    override fun queryProductDescription(site: String, idProduct: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}