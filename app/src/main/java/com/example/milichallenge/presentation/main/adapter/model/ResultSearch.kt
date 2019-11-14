package com.example.milachallenge.presentation.main.adapter.model

class ResultSearch(val query:String,val users: List<Product>) {
    fun toJson(): String {
      return ""
        //  return Gson().toJson(this)
    }
}