package com.example.milachallenge.presentation.main.adapter.model

data class Product(
    val siteId: String,
    val title: String,
    val seller: Seller,
    val price: Double,
    val currencyId: String,
    val availableQuantity: Integer,
    val soldQuantity: Integer,
    val condition: String,
    val thumbnail: String,
    val acceptsMercadoPago: Boolean,
    val installments: Installments,
    val shipping: Shipping,
    val sellerAddress: Address,
    val attributes: List<Attribute>
)

