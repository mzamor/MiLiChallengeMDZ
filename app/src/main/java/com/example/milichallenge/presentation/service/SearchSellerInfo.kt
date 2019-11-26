package com.example.milichallenge.presentation.service

import com.example.milichallenge.presentation.itemSelected.Model.SellerData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchSellerInfo {
    @GET("{site}/search")
    fun getSearchSellerInfo(
        @Path("site") site: String, @Query("seller_id") sellerId: String
    ): Call<SellerData>
}
