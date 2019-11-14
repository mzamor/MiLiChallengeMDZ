package com.example.milachallenge.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.presenter.MainPresenter
import com.example.milichallenge.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity:MainContract.MainView, AppCompatActivity() {

    lateinit var presenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter()
        presenter.attachView(this)

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.mercadolibre.com/sites/MLA/")

    }

    override fun navigateToItemDetails() {

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}