package com.example.milichallenge.presentation.itemSelected.presenter

import com.example.milachallenge.presentation.main.ItemDetailsContract
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductDescriptionInteractor
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchSellerInfoInteractor
import com.example.milichallenge.presentation.itemSelected.Model.ProductDescription
import com.example.milichallenge.presentation.itemSelected.Model.SellerData

class ItemDetailsPresenter(searchSellerInfoInteractor: SearchSellerInfoInteractor,
                           searchProductDescriptionInteractor: SearchProductDescriptionInteractor) :
    ItemDetailsContract.ItemDetailPresenter {
    var view: ItemDetailsContract.ItemDetailView? = null
    var searchSellerInfoInteractor: SearchSellerInfoInteractor? = null
    var searchProductDescriptionInteractor: SearchProductDescriptionInteractor? = null


    init {
        this.searchSellerInfoInteractor = searchSellerInfoInteractor
        this.searchProductDescriptionInteractor = searchProductDescriptionInteractor
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

    override fun queryProductDescription(idProduct: String) {
        view?.showProgressBar()
        searchProductDescriptionInteractor?.queryProductsDescription(idProduct, object: SearchProductDescriptionInteractor.SearchProductDescriptionCallback{
            override fun onSearchProductDescriptionSuccess(resultSearch: ProductDescription) {
                if (isViewAttached()) {
                    view?.hideProgressBar()
                    view?.showProductDescription(resultSearch)
                }
            }

            override fun onSearchProductDescriptionFailure(errorMsg: String) {
            view?.hideProgressBar()
            }
        })
    }

}