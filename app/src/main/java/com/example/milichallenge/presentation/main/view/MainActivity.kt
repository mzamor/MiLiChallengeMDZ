package com.example.milichallenge.presentation.main.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.itemSelected.ItemDetailsActivity
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.adapter.model.Product
import com.example.milachallenge.presentation.main.presenter.MainPresenter
import com.example.milichallenge.R
import com.example.milichallenge.presentation.domain.interactor.searchProducts.SearchProductInteractorImpl
import com.example.milichallenge.presentation.main.adapter.ProductsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MainContract.MainView, AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var presenter: MainPresenter
    private val siteQuery = "MLA"
    private val paginationStepNumber = 20
    private var rvProducts: RecyclerView? = null
    private var resultsProducts: List<Product>? = null
    private var svProductsMenu: SearchView? = null
    private var isLoading: Boolean = true
    private var previousTotal = 0
    private var visibleThreshold = 5
    private var pagingNumber = 0
    private var myQuery = ""
    private var isConnected = true
    private val productAdapter: ProductsAdapter by lazy {
        ProductsAdapter(
            this,
            object : ClickListener {
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
        if (savedInstanceState == null) {
            resultsProducts = ArrayList<Product>()
        }
        bindItems()

    }

    fun bindItems(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProducts?.layoutManager = linearLayoutManager
        rvProducts?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvProducts?.adapter = productAdapter
        rvProducts?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisible = linearLayoutManager.findFirstVisibleItemPosition()
                if (isConnected()) {
                    if (isLoading) {
                        if (previousTotal < totalItemCount) {
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }

                    if (!isLoading && (firstVisible + visibleThreshold) >= totalItemCount - visibleItemCount) {
                        pagingNumber += paginationStepNumber
                        isLoading = true
                        presenter.queryProducts(siteQuery, myQuery, pagingNumber)
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val gson = Gson()
        val productListJson = gson.toJson(resultsProducts)
        outState.putSerializable("PRODUCT_ARRAY_LIST", productListJson)
        outState.putInt("PAGING_NUMBER", pagingNumber)
        outState.putString("QUERY", myQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val gson = Gson()
        val listType = object : TypeToken<ArrayList<Product>>() {}.type
        resultsProducts = gson.fromJson<List<Product>>(
            savedInstanceState.getSerializable("PRODUCT_ARRAY_LIST").toString(),
            listType
        )
        showProductList(resultsProducts!!)
        pagingNumber = savedInstanceState.getInt("PAGING_NUMBER")
        myQuery = savedInstanceState.getString("QUERY")!!
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun navigateToItemDetails(view: View, product: Product) {
        val intent = Intent(this@MainActivity, ItemDetailsActivity::class.java)
        val gson = Gson()
        val productJson = gson.toJson(product)
        intent.putExtra("PRODUCT", productJson)
        startActivity(intent)
    }

    override fun showProductList(result: List<Product>) {
        productAdapter.addAll(result)
        resultsProducts = productAdapter.getProducts()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        myQuery = query!!
        productAdapter.clear()
        if (isConnected()) {
            presenter.queryProducts(siteQuery, query, pagingNumber)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        pagingNumber = 0
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        svProductsMenu = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        svProductsMenu?.inputType = InputType.TYPE_CLASS_TEXT
        svProductsMenu?.onActionViewExpanded()
        svProductsMenu?.queryHint = "Buscar en Mercado Libre"
        svProductsMenu?.setOnQueryTextListener(this)
        return true
    }

    fun isConnected(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        if (activeNetwork == null) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            tv_no_connection.visibility = View.VISIBLE
            isConnected = false
        } else {
            tv_no_connection.visibility = View.GONE
            isConnected = true
        }
        return isConnected
    }


    override fun showProgressBar() {
        pb_products.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pb_products.visibility = View.GONE
    }
}
