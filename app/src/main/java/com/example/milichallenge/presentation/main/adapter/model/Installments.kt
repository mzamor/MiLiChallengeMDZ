package com.example.milachallenge.presentation.main.adapter.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Installments(
    @SerializedName("quantity")
    val quantity: Integer,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("rate")
    val rate: Float,
    @SerializedName("currency_id")
    val currencyId: String
) : Serializable


