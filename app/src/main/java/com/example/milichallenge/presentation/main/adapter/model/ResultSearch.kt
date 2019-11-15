package com.example.milachallenge.presentation.main.adapter.model

import com.google.gson.annotations.SerializedName

data class ResultSearch(@SerializedName("query")val query:String, @SerializedName("results")val results: List<Product>)

