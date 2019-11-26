package com.example.milachallenge.presentation.main.adapter.model

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
    @SerializedName("original_price")
    val originalPrice: Double,
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
    val attributes: List<Attribute>,
    var ratingStars: Int,
    var commentsNumber: Int
) : Serializable {

    fun getStar(): Int {
        if (ratingStars == 0) {
            ratingStars = (0..5).random()
            return ratingStars
        }
        return ratingStars
    }

    fun getCommentNumber(): Int {
        if (commentsNumber == 0) {
            commentsNumber = (0..1000).random()
            return commentsNumber
        }
        return commentsNumber
    }
}

