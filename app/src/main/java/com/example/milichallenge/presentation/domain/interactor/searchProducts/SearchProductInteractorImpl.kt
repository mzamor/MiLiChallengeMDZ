package com.example.milichallenge.presentation.domain.interactor.searchProducts

import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import com.example.milichallenge.presentation.service.SearchServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchProductInteractorImpl : SearchProductInteractor {
    private val urlBase = "https://api.mercadolibre.com/sites/"
    private val limitSearch = "20"
    override fun queryProducts(
        site: String,
        productSearch: String,
        pagingNumber: Int,
        listener: SearchProductInteractor.SearchProductsCallback
    ) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(urlBase).build()
        val service = retrofit.create(SearchServices::class.java)
        val call = service.getSearchProducts(site, productSearch, pagingNumber.toString(), limitSearch)
        call.enqueue(object : Callback<ResultSearch> {
            override fun onResponse(call: Call<ResultSearch>, response: Response<ResultSearch>) {
                val resultSearch: ResultSearch = response.body()!!
                listener.onSearchProductsSuccess(resultSearch)
            }

            override fun onFailure(call: Call<ResultSearch>, t: Throwable) {
                listener.onSearchProductsFailure(t.message.toString())
            }
        })
    }
}