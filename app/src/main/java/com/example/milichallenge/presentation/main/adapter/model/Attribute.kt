package com.example.milachallenge.presentation.main.adapter.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Attribute(@SerializedName("name")val name: String, @SerializedName("value_id")val valueId: String, @SerializedName("value_name") val valueName: String):Serializable

