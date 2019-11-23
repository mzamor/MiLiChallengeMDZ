package com.example.milichallenge.presentation.main.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlin.math.roundToInt

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
            itemView.tv_price.text = String.format(context.getString(R.string.price), product.price.roundToInt().toString())
            if(product.installments != null){
                Log.e("quantity",product.installments.quantity.toString())
                itemView.tv_installments.text = String.format(context.getString(R.string.installments), product.installments.quantity.toString())
                itemView.tv_free_shipping.visibility = if (product.shipping.freeShipping) VISIBLE else GONE
            }
            itemView.rb_stars_number.setRating(product.getStar().toFloat())
            itemView.tv_comments_number.text = product.getCommentNumber().toString()
            itemView.setOnClickListener(View.OnClickListener {
                listener.onClick(it, product)
            })
        }
    }
}