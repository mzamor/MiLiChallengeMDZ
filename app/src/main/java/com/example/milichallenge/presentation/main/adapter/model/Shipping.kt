package com.example.milachallenge.presentation.main.adapter.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Shipping(@SerializedName("free_shipping") val freeShipping: Boolean) : Serializable