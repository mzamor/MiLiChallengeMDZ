package com.example.milichallenge.presentation.service

import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchServices {
    @GET("/{capital}")
    fun getSearchProducts(@Path("iphone") productSearch: String): Call<ResultSearch>
}
