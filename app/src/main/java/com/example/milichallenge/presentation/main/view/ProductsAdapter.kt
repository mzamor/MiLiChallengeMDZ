package com.example.milichallenge.presentation.main.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milichallenge.R
import kotlinx.android.synthetic.main.item_product_list.view.*

class ProductsAdapter(private val context: Context) : RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
    private var productsList:List<Product>

    init{
        productsList = emptyList<Product>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        return ProductsHolder(LayoutInflater.from(context).inflate(R.layout.item_product_list,parent,false))
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.bind(productsList[position])
    }

    fun add(productList:List<Product>){
            productsList = productList
            notifyDataSetChanged()
    }

    class ProductsHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(product: Product) {
            itemView.tv_title.text = product.title
            itemView.tv_price.text = product.price.toString()
        }

    }
}