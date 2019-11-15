package com.example.milichallenge.presentation.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import com.example.milachallenge.presentation.main.presenter.MainPresenter
import com.example.milichallenge.R
import com.example.milichallenge.presentation.service.SearchServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : MainContract.MainView, AppCompatActivity() {
    lateinit var presenter : MainPresenter
    private lateinit var rvProducts:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvProducts = findViewById(R.id.rv_products)
        presenter = MainPresenter()
        presenter.attachView(this)
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.mercadolibre.com/sites/").build()
        val service = retrofit.create(SearchServices::class.java)
        val call = service.getSearchProducts("MLA","iphone")
        call.enqueue(object:Callback<ResultSearch>{
            override fun onResponse(call: Call<ResultSearch>, response: Response<ResultSearch>) {
                Log.e("response", response.body().toString())

            }
            override fun onFailure(call: Call<ResultSearch>, t: Throwable) {
            }

        })
    }

    override fun navigateToItemDetails() {
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
