package com.example.milachallenge.presentation.itemSelected

import android.os.Bundle
import android.view.MenuItem
import android.view.View
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

class ItemDetailsActivity : AppCompatActivity() {
    private var product: Product? = null
    var rvProductInfo: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.product)
        rvProductInfo = findViewById(R.id.rv_product_info)
        var soldProduct : String
        val intent = intent
        val productJson = intent.getStringExtra("PRODUCT")
        val gson = Gson()
        product = gson.fromJson(productJson, Product::class.java)
        tv_new_products.text = if(product?.condition!=null && product?.condition.toString().equals("new")) getText(R.string.new_product) else ""
        tv_new_products.visibility = if(tv_new_products.text.isNotEmpty()) View.VISIBLE else View.GONE
        soldProduct = if(product?.soldQuantity!! > 1) getString(R.string.sold_more_than_one_product) else getString(R.string.sold_one_product)
        tv_sold_products.text = String.format( soldProduct, product?.soldQuantity.toString())
        tv_sold_products.text = if(tv_new_products.text.isNotEmpty()) String.format(getString(R.string.bar_separator),tv_sold_products.text) else String.format(soldProduct, product?.soldQuantity.toString())
        tv_sold_products.visibility = if(product?.soldQuantity!! > 0) View.VISIBLE else View.GONE
        tv_title_product_detail.text = product?.title
        rb_stars_number_detail.setRating(product?.getStar()!!.toFloat())
        tv_comments_number_detail.text  = product?.getCommentNumber().toString()
        Picasso.get().load(product?.thumbnail).into(iv_product_detail)
        tv_price_product_detail.text =
            String.format(getString(R.string.price), product?.price.toString())
        productInfo()
    }

    fun productInfo() {
        rvProductInfo?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProductInfo?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvProductInfo?.isNestedScrollingEnabled = false;
        rvProductInfo?.adapter = ProductInfoAdapter(
            this,
            filterAttributeById(getString(R.string.item_status), product?.attributes!!)
        )
    }

    fun filterAttributeById(valueId: String, lvProductInfo: List<Attribute>): List<Attribute> {
        return lvProductInfo.filter { s -> s.valueId != valueId }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }
}
