package com.example.milichallenge.presentation.itemSelected.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Seller(
    @SerializedName("id")
    val idSeller: Int,
    @SerializedName("nickname")
    val nickName: String,
    @SerializedName("eshop")
    val eShop: Eshop
) : Serializable