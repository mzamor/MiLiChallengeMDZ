package com.example.milichallenge.presentation.main.view

import android.view.View
import com.example.milachallenge.presentation.main.adapter.model.Product

interface ClickListener {
    fun onClick(view: View, product: Product)
}