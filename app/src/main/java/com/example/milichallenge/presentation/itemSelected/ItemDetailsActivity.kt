package com.example.milachallenge.presentation.itemSelected

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.example.milichallenge.presentation.itemSelected.ProductInfoAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity() {
    private var product: Product? = null
    var lvProductInfo: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        lvProductInfo = findViewById(R.id.lv_product_info)
        val intent = intent
        val productJson = intent.getStringExtra("PRODUCT")
        val gson = Gson()
        product = gson.fromJson(productJson, Product::class.java)
        tv_title_product_detail.text = product?.title
        tv_price_product_detail.text =
            String.format(getString(R.string.price), product?.price.toString())
        productInfo()
    }

    fun productInfo() {
        lvProductInfo?.adapter = ProductInfoAdapter(this, product?.attributes!!)
    }
}
