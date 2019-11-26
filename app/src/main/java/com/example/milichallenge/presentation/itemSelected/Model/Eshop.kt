package com.example.milichallenge.presentation.itemSelected.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Eshop(
    @SerializedName("eshop_logo_url") val urlLogo: String
) : Serializable