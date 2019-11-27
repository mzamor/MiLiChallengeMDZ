package com.example.milachallenge.presentation.itemSelected

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.ItemDetailsContract
import com.example.milachallenge.presentation.main.adapter.model.Attribute
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchSellerInfoInteractorImpl
import com.example.milichallenge.presentation.itemSelected.Model.SellerData
import com.example.milichallenge.presentation.itemSelected.adapter.ProductInfoAdapter
import com.example.milichallenge.presentation.itemSelected.presenter.ItemDetailsPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_details.*
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.SpannableString
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import kotlinx.android.synthetic.main.item_product_list.view.*


class ItemDetailsActivity : AppCompatActivity(), ItemDetailsContract.ItemDetailView {
    private lateinit var itemDetailsPresenter: ItemDetailsPresenter
    private var product: Product? = null
    var rvProductInfo: RecyclerView? = null
    private val siteQuery = "MLA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.product)
        rvProductInfo = findViewById(R.id.rv_product_info)
        bindDetails()
        itemDetailsPresenter.querySellerInfo(siteQuery, product?.seller?.id!!)
        productInfo()
    }


    fun bindDetails(){
        var soldProduct: String
        val intent = intent
        val productJson = intent.getStringExtra("PRODUCT")
        val gson = Gson()
        product = gson.fromJson(productJson, Product::class.java)
        itemDetailsPresenter = ItemDetailsPresenter(SearchSellerInfoInteractorImpl())
        itemDetailsPresenter.attachView(this)
        tv_new_products.text = if (product?.condition != null && product?.condition.toString().equals("new")) getText(R.string.new_product) else ""
        tv_new_products.visibility = if (tv_new_products.text.isNotEmpty()) View.VISIBLE else View.GONE
        soldProduct =
            if (product?.soldQuantity!! > 1) getString(R.string.sold_more_than_one_product) else getString(
                R.string.sold_one_product
            )
        tv_sold_products.text = String.format(soldProduct, product?.soldQuantity.toString())
        tv_sold_products.text = if (tv_new_products.text.isNotEmpty()) String.format(
            getString(R.string.bar_separator), tv_sold_products.text
        ) else String.format(soldProduct, product?.soldQuantity.toString())
        tv_sold_products.visibility = if (product?.soldQuantity!! > 0) View.VISIBLE else View.GONE
        tv_title_product_detail.text = product?.title
        rb_stars_number_detail.setRating(product?.getStar()!!.toFloat())
        tv_comments_number_detail.text = product?.getCommentNumber().toString()
        Picasso.get().load(product?.thumbnail).into(iv_product_detail)
        tv_real_price_product_detail.text = String.format(getString(R.string.price_with_strike),product?.originalPrice.toString())
        tv_real_price_product_detail.paintFlags = tv_real_price_product_detail.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        tv_real_price_product_detail.visibility = if(product?.originalPrice!! > 0) View.VISIBLE else View.GONE
        var intPartPrice = product?.price?.toInt()
        tv_price_product_detail.text = String.format(getString(R.string.price),
            if(product?.price!! - intPartPrice!!.toDouble() > 0) product?.price.toString() else intPartPrice.toString())
        bt_quantity_products.setCompoundDrawables(null,null,resources.getDrawable(R.drawable.ic_navigate_next_black_24dp),null)
        bt_quantity_products.text = String.format(getString(R.string.quantity_product_selected),"1",if(product?.availableQuantity!! > 1) product?.availableQuantity.toString() else getString(R.string.last_product))
        tv_mercado_puntos_product_detail.text = String.format(getString(R.string.mercado_puntos),(intPartPrice * 0.05).toInt().toString())
    }

    fun productInfo() {
        rvProductInfo?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProductInfo?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvProductInfo?.isNestedScrollingEnabled = false
        rvProductInfo?.adapter =
            ProductInfoAdapter(
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

    override fun showSellerInfo(result: SellerData) {
        var sizeTextSeller = getString(R.string.sold_it_by).length - 3
        var sellerText = String.format(getString(R.string.sold_it_by), result.seller.nickName)
        val ss1 = SpannableString(sellerText)
        ss1.setSpan(RelativeSizeSpan(1.1f), sizeTextSeller, ss1.length, 0)
        ss1.setSpan(ForegroundColorSpan(Color.BLUE), sizeTextSeller, ss1.length, 0)
        tv_sold_it_by.text = ss1
    }

    override fun showProgressBar() {
        pb_item_details.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pb_item_details.visibility = View.GONE
    }


}
