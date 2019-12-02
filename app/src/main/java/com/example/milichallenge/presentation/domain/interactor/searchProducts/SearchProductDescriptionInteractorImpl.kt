package com.example.milichallenge.presentation.domain.interactor.searchProducts

import com.example.milichallenge.presentation.itemSelected.Model.ProductDescription
import com.example.milichallenge.presentation.service.SearchProductDescription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchProductDescriptionInteractorImpl : SearchProductDescriptionInteractor {
    private val urlBase = "https://api.mercadolibre.com/items/"
    override fun queryProductsDescription(
        productId: String,
        listener: SearchProductDescriptionInteractor.SearchProductDescriptionCallback
    ) {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(urlBase).build()
        val service = retrofit.create(SearchProductDescription::class.java)
        val call = service.getSearchProductInfo(productId)
        call.enqueue(object : Callback<ProductDescription> {
            override fun onResponse(
                call: Call<ProductDescription>,
                response: Response<ProductDescription>
            ) {
                val productDescription: ProductDescription = response.body()!!
                listener.onSearchProductDescriptionSuccess(productDescription)
            }

            override fun onFailure(call: Call<ProductDescription>, t: Throwable) {
                listener.onSearchProductDescriptionFailure(t.message.toString())
            }
        })


    }


}