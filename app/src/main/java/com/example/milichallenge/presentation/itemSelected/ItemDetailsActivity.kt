package com.example.milachallenge.presentation.itemSelected

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity() {
    private var product : Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        val intent = intent
        val productJson = intent.getStringExtra("PRODUCT")
        Log.e("json", productJson)
        val gson = Gson()
        product = gson.fromJson(productJson, Product::class.java)
        tv_title_product_detail.text = product?.title
        productData()
    }

    fun productData(){

    }
}
