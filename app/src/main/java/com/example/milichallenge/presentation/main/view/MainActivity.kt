package com.example.milichallenge.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.itemSelected.ItemDetailsActivity
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milachallenge.presentation.main.adapter.model.ResultSearch
import com.example.milachallenge.presentation.main.presenter.MainPresenter
import com.example.milichallenge.R
import com.google.gson.Gson

class MainActivity : MainContract.MainView, AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var presenter: MainPresenter
    private var rvProducts: RecyclerView? = null
    private var svProductsMenu: SearchView? = null
    private val productAdapter: ProductsAdapter by lazy {
        ProductsAdapter(this, object : ClickListener {
            override fun onClick(view: View, product: Product) {
                navigateToItemDetails(view, product)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvProducts = findViewById(R.id.rv_products)
        presenter = MainPresenter()
        presenter.attachView(this)
    }

    override fun navigateToItemDetails(view: View, product: Product) {
        val intent = Intent(this@MainActivity, ItemDetailsActivity::class.java)
        val gson = Gson()
        val productJson = gson.toJson(product)
        intent.putExtra("PRODUCT", productJson)
        startActivity(intent)
    }

    override fun showProductList(resultSearch: ResultSearch) {
        rvProducts?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProducts?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvProducts?.adapter = productAdapter
        productAdapter.add(resultSearch.results)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.queryProducts("MLA", query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e("newText", newText)
        //   presenter.queryProducts("MLA",newText!!)
        return false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        svProductsMenu = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        svProductsMenu?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        svProductsMenu?.setOnQueryTextListener(this)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
