package com.example.milichallenge.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.presenter.MainPresenter
import com.example.milichallenge.R

class MainActivity : MainContract.MainView, AppCompatActivity() {

    lateinit var presenter: MainPresenter
    private lateinit var rvProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvProducts = findViewById(R.id.rv_products)
        presenter = MainPresenter()
        presenter.attachView(this)
        presenter.queryProducts("MLA","iphone")
    }

    override fun navigateToItemDetails() {
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
