package com.example.milichallenge.presentation.service

import com.example.milichallenge.presentation.itemSelected.Model.ProductDescription
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchProductDescription {
    @GET("{seller_id}/description")
    fun getSearchProductInfo(
        @Path("seller_id") productId: String
    ): Call<ProductDescription>
}
