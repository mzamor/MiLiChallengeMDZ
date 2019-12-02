package com.example.milachallenge.presentation.itemSelected

import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.ItemDetailsContract
import com.example.milachallenge.presentation.main.adapter.model.Attribute
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductDescriptionInteractorImpl
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchSellerInfoInteractorImpl
import com.example.milichallenge.presentation.itemSelected.Model.ProductDescription
import com.example.milichallenge.presentation.itemSelected.Model.SellerData
import com.example.milichallenge.presentation.itemSelected.adapter.ProductInfoAdapter
import com.example.milichallenge.presentation.itemSelected.presenter.ItemDetailsPresenter
import com.example.milichallenge.presentation.itemSelected.view.ProductQuantityDialogFragment
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity(), ItemDetailsContract.ItemDetailView,
    ProductQuantityDialogFragment.ProductQuantity {
    private lateinit var itemDetailsPresenter: ItemDetailsPresenter
    private var product: Product? = null
    var rvProductInfo: RecyclerView? = null
    private val SITE_QUERY = "MLA"
    private val NEW_PRODUCT = "new"
    var soldProduct: String = ""
    var builder: AlertDialog.Builder? = null
    var array: Array<String>? = null
    var productQuantity: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.product)
        rvProductInfo = findViewById(R.id.rv_product_info)
        bindDetails()
        itemDetailsPresenter.querySellerInfo(SITE_QUERY, product?.seller?.id!!)
        itemDetailsPresenter.queryProductDescription(product?.id.toString())
        productInfo()
    }

    fun bindDetails() {
        val intent = intent
        val productJson = intent.getStringExtra("PRODUCT")
        val gson = Gson()
        product = gson.fromJson(productJson, Product::class.java)
        itemDetailsPresenter = ItemDetailsPresenter(
            SearchSellerInfoInteractorImpl(),
            SearchProductDescriptionInteractorImpl()
        )
        itemDetailsPresenter.attachView(this)
        tv_new_products.text =
            if (product?.condition != null && product?.condition.toString() == NEW_PRODUCT) getText(
                R.string.new_product
            ) else ""
        tv_new_products.visibility =
            if (tv_new_products.text.isNotEmpty()) View.VISIBLE else View.GONE
        setQuantitySoldProducts()
        tv_title_product_detail.text = product?.title
        setCommentAndRanking()
        setModePayment()
        Picasso.get().load(product?.thumbnail).into(iv_product_detail)
        setPrices()
        setQuantityAvailableProducts()
        tv_mercado_puntos_product_detail.text = String.format(
            getString(R.string.mercado_puntos),
            calculateMercadoPuntos(product?.price!!)
        )
    }


    fun setQuantitySoldProducts() {
        soldProduct =
            if (product?.soldQuantity!! > 1) getString(R.string.sold_more_than_one_product) else getString(
                R.string.sold_one_product
            )
        tv_sold_products.text = String.format(soldProduct, product?.soldQuantity.toString())
        tv_sold_products.text = if (tv_new_products.text.isNotEmpty()) String.format(
            getString(R.string.bar_separator), tv_sold_products.text
        ) else String.format(soldProduct, product?.soldQuantity.toString())
        tv_sold_products.visibility = if (product?.soldQuantity!! > 0) View.VISIBLE else View.GONE
    }

    fun setCommentAndRanking() {
        rb_stars_number_detail.setRating(product?.getStar()!!.toFloat())
        tv_comments_number_detail.text = product?.getCommentNumber().toString()
    }

    fun setModePayment() {
        tv_payment_way_product_detail.text = String.format(
            getString(R.string.payment_way),
            product?.installments?.quantity.toString()
        )
    }

    fun setPrices() {
        tv_real_price_product_detail.text =
            String.format(getString(R.string.price_with_strike), product?.originalPrice.toString())
        tv_real_price_product_detail.paintFlags =
            tv_real_price_product_detail.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        tv_real_price_product_detail.visibility =
            if (hasSale(product?.originalPrice!!)) View.VISIBLE else View.GONE
        tv_sale_discount_product_detail.visibility =
            if (hasSale(product?.originalPrice!!)) View.VISIBLE else View.GONE
        tv_price_product_detail.text =
            String.format(getString(R.string.price), hasDecimal(product?.price!!))
        tv_sale_discount_product_detail.text = String.format(
            getString(R.string.sale_discount),
            if (hasSale(product?.originalPrice!!)) percentDiscount(
                product?.originalPrice!!,
                product?.price!!
            ) else " "
        )
        tv_sale_discount_product_detail.visibility =
            if (hasSale(product?.originalPrice!!)) View.VISIBLE else View.GONE
    }

    fun hasSale(originalPrice: Double): Boolean {
        return originalPrice > 0
    }

    fun hasDecimal(price: Double): String {
        val intPartPrice = price.toInt()
        return if (price - intPartPrice.toDouble() > 0) price.toString() else intPartPrice.toString()
    }

    fun setQuantityAvailableProducts() {
        bt_quantity_products.text = String.format(
            getString(R.string.quantity_product_selected), productQuantity.toString(),
            if (product?.availableQuantity!! > 1) String.format(
                getString(R.string.last_products),
                product?.availableQuantity.toString()
            ) else getString(R.string.last_product)
        )
        bt_quantity_products.setOnClickListener {
            setDialog()
        }
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
        ss1.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.light_blue)),
            sizeTextSeller,
            ss1.length,
            0
        )
        tv_sold_it_by.text = ss1
    }

    override fun showProductDescription(result: ProductDescription) {
        tv_product_description.text = result.description
    }

    override fun showProgressBar() {
        pb_item_details.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pb_item_details.visibility = View.GONE
    }

    fun percentDiscount(originalPrice: Double, price: Double): String {
        return (100 - (price * 100 / originalPrice).toInt()).toString()
    }

    fun calculateMercadoPuntos(value: Double): String {
        return (value.toInt() * 0.05).toInt().toString()
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

    private fun setDialog() {
        val productQuantityDialogFragment =
            ProductQuantityDialogFragment(product?.availableQuantity?.toInt())
        productQuantityDialogFragment.show(supportFragmentManager, "productQuantity")
    }

    override fun productQuantitySelected(quantity: Int) {
        productQuantity = quantity
        setQuantityAvailableProducts()
    }
}


