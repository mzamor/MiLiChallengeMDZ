package com.example.milichallenge.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductInteractorImpl
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MainContract.MainView, AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var presenter: MainPresenter
    private var rvProducts: RecyclerView? = null
    private var svProductsMenu: SearchView? = null
    private var pagingNumber = 0
    private var myQuery = ""

    private val onScrollHandler = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = linearLayoutManager.childCount
            val totalItemCount = linearLayoutManager.itemCount
            val firstVisible = linearLayoutManager.findFirstVisibleItemPosition()
            if ( (visibleItemCount + firstVisible) >= totalItemCount) {
                pagingNumber=+6
                presenter.queryProducts("MLA", myQuery,pagingNumber)
            }
        }
    }

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
        presenter = MainPresenter(SearchProductInteractorImpl())
        presenter.attachView(this)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        rvProducts?.apply {
            layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            adapter = productAdapter
            addOnScrollListener(onScrollHandler)
        }
    }

    override fun navigateToItemDetails(view: View, product: Product) {
        val intent = Intent(this@MainActivity, ItemDetailsActivity::class.java)
        val gson = Gson()
        val productJson = gson.toJson(product)
        intent.putExtra("PRODUCT", productJson)
        startActivity(intent)
    }

    override fun showProductList(resultSearch: ResultSearch) {
            productAdapter.addAll(resultSearch.results)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        pagingNumber = 0
        myQuery = query!!
        productAdapter.clear()
        presenter.queryProducts("MLA", query!!,pagingNumber)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
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

    override fun showProgressBar() {
        pb_products.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pb_products.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
