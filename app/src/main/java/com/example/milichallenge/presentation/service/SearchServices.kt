package com.example.milichallenge.presentation.service

import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchServices {
    @GET("/search")
    fun getSearchProducts(@Query("q") productSearch: String): Call<ResultSearch>
}
