package com.example.milichallenge.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.example.milichallenge.presentation.main.view.ClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductsAdapter(private val context: Context, var listener: ClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
    var productsList: MutableList<Product>
    var myContext: Context? = null

    init {
        productsList = ArrayList()
        myContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        return ProductsHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_product_list,
                parent,
                false
            ), listener
        )
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {

        holder.bind(myContext!!, productsList[position], listener)
    }

    fun addAll(productList: List<Product>) {
        productsList.addAll(productList)
        notifyDataSetChanged()
    }

    fun getProducts(): List<Product> {
        return productsList
    }

    fun clear() {
        productsList.clear()
        notifyDataSetChanged()
    }

    class ProductsHolder(itemView: View, listener: ClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var listener: ClickListener? = null

        init {
            this.listener = listener
        }

        fun bind(context: Context, product: Product, listener: ClickListener) {
            Picasso.get().load(product.thumbnail).into(itemView.iv_product)
            itemView.tv_title.text = product.title
            itemView.tv_price.text = String.format(
                context.getString(R.string.price), product.currencyId,
                getDoubleValueIfHasDecimals(product.price)
            )
            itemView.tv_sale.text = String.format(
                context.getString(R.string.sale_discount),
                getDiscountIfHasSale(product.originalPrice, product.price)
            )
            itemView.tv_sale.visibility = if (product.originalPrice > 0) VISIBLE else GONE

            if (product.installments != null) {
                itemView.tv_installments.text = String.format(
                    context.getString(R.string.installments),
                    product.installments.quantity.toString()
                )
                itemView.tv_installments.visibility = VISIBLE

            }else{
                itemView.tv_installments.visibility = GONE
            }

            itemView.tv_free_shipping.visibility =
                if (product.shipping.freeShipping) VISIBLE else GONE
            itemView.rb_stars_number.setRating(product.getStar().toFloat())
            itemView.tv_comments_number.text = product.getCommentNumber().toString()
            itemView.setOnClickListener(View.OnClickListener {
                listener.onClick(it, product)
            })
        }

        fun percentDiscount(originalPrice: Double, price: Double): String {
            return (100 - (price * 100 / originalPrice).toInt()).toString()
        }

        fun hasDecimal(value: Double): Boolean {
            var intPartPrice = value.toInt()
            return (value - intPartPrice.toDouble()) > 0
        }

        fun getDoubleValueIfHasDecimals(value: Double): String {
            return if (hasDecimal(value)) value.toString() else value.toInt().toString()
        }

        fun getDiscountIfHasSale(originalPrice: Double, price: Double): String {
            return if (originalPrice > 0) percentDiscount(originalPrice, price) else " "
        }
    }
}