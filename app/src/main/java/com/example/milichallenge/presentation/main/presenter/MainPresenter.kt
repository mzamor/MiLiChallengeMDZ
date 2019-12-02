package com.example.milachallenge.presentation.main.presenter

import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductInteractor

class MainPresenter(searchProductInteractor: SearchProductInteractor) : MainContract.MainPresenter {
    var view: MainContract.MainView? = null
    var searchProductInteractor: SearchProductInteractor? = null

    init {
        this.searchProductInteractor = searchProductInteractor
    }

    override fun queryProducts(site: String, productSearch: String, pagingNumber: Int) {
        view?.showProgressBar()
        this.searchProductInteractor?.queryProducts(
            site,
            productSearch,
            pagingNumber,
            object : SearchProductInteractor.SearchProductsCallback {
                override fun onSearchProductsSuccess(result: List<Product>) {
                    if (isViewAttached()) {
                        view?.hideProgressBar()
                        view?.showProductList(result)
                    }
                }

                override fun onSearchProductsFailure(errorMsg: String) {
                    if (isViewAttached()) {
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

    override fun isViewAttached(): Boolean {
        return view != null
    }

}