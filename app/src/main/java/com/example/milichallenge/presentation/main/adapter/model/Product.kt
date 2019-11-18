package com.example.milachallenge.presentation.main.adapter.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("site_id")
    val siteId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("seller")
    val seller: Seller,
    @SerializedName("price")
    val price: Double,
    @SerializedName("currency_id")
    val currencyId: String,
    @SerializedName("available_quantity")
    val availableQuantity: Integer,
    @SerializedName("sold_quantity")
    val soldQuantity: Integer,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("accepts_mercadopago")
    val acceptsMercadoPago: Boolean,
    @SerializedName("installments")
    val installments: Installments,
    @SerializedName("shipping")
    val shipping: Shipping,
    @SerializedName("seller_address")
    val sellerAddress: Address,
    @SerializedName("attributes")
    val attributes: List<Attribute>
):Serializable

