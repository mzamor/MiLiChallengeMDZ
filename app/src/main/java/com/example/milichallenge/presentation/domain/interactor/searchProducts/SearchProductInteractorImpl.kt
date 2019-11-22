package com.example.milichallenge.presentation.domain.interactor.searchProducts

import android.util.Log
import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import com.example.milichallenge.presentation.service.SearchServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchProductInteractorImpl : SearchProductInteractor {
    override fun queryProducts(site: String, productSearch: String, pagingNumber: Int, listener : SearchProductInteractor.SearchProductsCallback) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.mercadolibre.com/sites/").build()
        val service = retrofit.create(SearchServices::class.java)
        val call = service.getSearchProducts(site, productSearch,pagingNumber.toString(),"6")
        call.enqueue(object : Callback<ResultSearch> {
            override fun onResponse(call: Call<ResultSearch>, response: Response<ResultSearch>) {
                Log.e("response", response.body().toString())
                val resultSearch: ResultSearch = response.body()!!
                listener.onSearchProductsSuccess(resultSearch)

            }

            override fun onFailure(call: Call<ResultSearch>, t: Throwable) {
                listener.onSearchProductsFailure(t.message.toString())
            }
        })
    }
}