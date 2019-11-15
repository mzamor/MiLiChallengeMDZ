package com.example.milachallenge.presentation.main.adapter.model

import com.google.gson.annotations.SerializedName

data class Address(@SerializedName("state_name") val stateName: String, @SerializedName("city_name") val cityName: String)