package com.example.milichallenge.presentation.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductsAdapter(private val context: Context, var listener: ClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
    var productsList: List<Product>
    var myContext : Context?=null
    init {
        productsList = emptyList()
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
        holder.bind(myContext!!,productsList[position], listener)
    }

    fun add(productList: List<Product>) {
        productsList = productList
        notifyDataSetChanged()
    }

    class ProductsHolder(itemView: View, listener: ClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var listener: ClickListener? = null

        init {
            this.listener = listener
        }

        fun bind(context:Context,product: Product, listener: ClickListener) {
            Picasso.get().load(product.thumbnail).into(itemView.iv_product)
            itemView.tv_title.text = product.title
            itemView.tv_price.text = String.format(context.getString(R.string.price),product?.price.toString())
            itemView.setOnClickListener(View.OnClickListener {
                listener.onClick(it, product)
            })

        }
    }
}