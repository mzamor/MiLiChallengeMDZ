package com.example.milachallenge.presentation.itemSelected

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.adapter.model.Attribute
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.example.milichallenge.presentation.itemSelected.ProductInfoAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_details.*
import kotlinx.android.synthetic.main.item_product_list.view.*

class ItemDetailsActivity : AppCompatActivity() {
    private var product: Product? = null
    var rvProductInfo: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        rvProductInfo = findViewById(R.id.rv_product_info)
        val intent = intent
        val productJson = intent.getStringExtra("PRODUCT")
        val gson = Gson()
        product = gson.fromJson(productJson, Product::class.java)
        tv_title_product_detail.text = product?.title
        tv_price_product_detail.text =
            String.format(getString(R.string.price), product?.price.toString())
        Picasso.get().load(product?.thumbnail).into(iv_product_detail)

        productInfo()
    }

    fun productInfo() {
        rvProductInfo?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProductInfo?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvProductInfo?.isNestedScrollingEnabled = false;
        rvProductInfo?.adapter = ProductInfoAdapter(this, filterAttributeById(getString(R.string.item_status),product?.attributes!!))
    }

    fun filterAttributeById( valueId:String, lvProductInfo: List<Attribute>) : List<Attribute> {
        return lvProductInfo.filter { s -> s.valueId != valueId }
    }
}
