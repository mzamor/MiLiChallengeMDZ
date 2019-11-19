package com.example.milichallenge.presentation.itemSelected

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.milachallenge.presentation.main.adapter.model.Attribute
import com.example.milichallenge.R
import com.example.milichallenge.presentation.main.view.ProductsAdapter

class ProductInfoAdapter(context : Context, attributes : List<Attribute>) : BaseAdapter() {
    var attributes : List<Attribute>? = null
    var context : Context? = null
    init {
        this.attributes = attributes
        this.context = context
    }

    override fun getItem(position: Int): Any {
        return attributes?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.attributes?.size!!
    }

    override fun getView(position: Int, itemView: View?, parent: ViewGroup?): View {
        val view : View?
        val productInfoHolder : ProductInfoHolder
        if(itemView==null){
            view = LayoutInflater.from(this.context).inflate(R.layout.row_product_info,parent,false)
            productInfoHolder = ProductInfoHolder(view)
            productInfoHolder.tvInfoProductName?.text = attributes?.get(position)?.name
            productInfoHolder.tvInfoProductValue?.text = attributes?.get(position)?.valueName
            productInfoHolder.tvInfoProductName?.setBackgroundResource(if(position%2!=0) R.color.LightGrey else R.color.gray)
            productInfoHolder.tvInfoProductValue?.setBackgroundResource(if(position%2!=0) R.color.white else R.color.LightGrey)

            view.tag = productInfoHolder
        } else{
            view = itemView
            productInfoHolder = view.tag as ProductInfoHolder
        }
        return view!!
    }

    private class ProductInfoHolder(row:View?){
        var tvInfoProductName: TextView?=null
        var tvInfoProductValue : TextView?=null
        init {
            tvInfoProductName = row?.findViewById(R.id.tv_info_product_name)
            tvInfoProductValue = row?.findViewById(R.id.tv_info_product_value)

        }
    }

}