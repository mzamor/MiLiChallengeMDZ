package com.example.milichallenge.presentation.itemSelected.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.adapter.model.Attribute
import com.example.milichallenge.R
import kotlinx.android.synthetic.main.row_product_info.view.*

class ProductInfoAdapter(context: Context, attributes: List<Attribute>) :
    RecyclerView.Adapter<ProductInfoAdapter.ProductInfoHolder>() {
    var attributesList: List<Attribute>? = null
    var myContext: Context? = null

    init {
        attributesList = attributes
        myContext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInfoHolder {
        return ProductInfoHolder(
            LayoutInflater.from(myContext).inflate(
                R.layout.row_product_info,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return this.attributesList?.size!!
    }

    override fun onBindViewHolder(holder: ProductInfoHolder, position: Int) {
        holder.bind(myContext!!, attributesList?.get(position), position)

    }


    class ProductInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context, attribute: Attribute?, position: Int) {
            itemView.tv_info_product_name.text = attribute?.name
            itemView.tv_info_product_value.text = attribute?.valueName
            itemView.tv_info_product_name.setBackgroundResource(if (position % 2 != 0) R.color.greyTableLight else R.color.greyTableDark)
            itemView.tv_info_product_value.setBackgroundResource(if (position % 2 != 0) R.color.greyTableUltraLight else R.color.greyTableLight)
        }
    }

}

