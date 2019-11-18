package com.example.milachallenge.presentation.main.adapter.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Seller(@SerializedName("id") val id: String, @SerializedName("power_seller_status")val powerSellerStatus: String, @SerializedName("card_dealer") val carDealer: Boolean) : Serializable


