package com.example.milichallenge.presentation.domain.interactor.searchProducts

import com.example.milichallenge.presentation.itemSelected.Model.SellerData
import com.example.milichallenge.presentation.service.SearchSellerInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchSellerInfoInteractorImpl : SearchSellerInfoInteractor {
    private val urlBase = "https://api.mercadolibre.com/sites/"
    override fun queryProducts(
        site: String,
        sellerId: String,
        listener: SearchSellerInfoInteractor.SearchSellerInfoCallback
    ) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(urlBase).build()
        val service = retrofit.create(SearchSellerInfo::class.java)
        val call = service.getSearchSellerInfo(site, sellerId)
        call.enqueue(object : Callback<SellerData> {
            override fun onResponse(call: Call<SellerData>, response: Response<SellerData>) {
                val sellerData: SellerData = response.body()!!
                listener.onSearchSellerInfoSuccess(sellerData)
            }

            override fun onFailure(call: Call<SellerData>, t: Throwable) {
                listener.onSearchSellerInfoFailure(call.toString())
            }
        })
    }
}