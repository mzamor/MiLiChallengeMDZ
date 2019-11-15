package com.example.milichallenge.presentation.main.view

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.milachallenge.presentation.main.MainContract
import com.example.milachallenge.presentation.main.presenter.MainPresenter
import com.example.milichallenge.R

class MainActivity : MainContract.MainView, AppCompatActivity(),  SearchView.OnQueryTextListener  {
    lateinit var presenter: MainPresenter
    private var rvProducts: RecyclerView? = null
    private var svProductsMenu: SearchView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvProducts = findViewById(R.id.rv_products)
        presenter = MainPresenter()
        presenter.attachView(this)
    }

    override fun navigateToItemDetails() {
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.e("query",query)
        presenter.queryProducts("MLA",query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e("newText",newText)
        presenter.queryProducts("MLA",newText!!)
        return false    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        svProductsMenu = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        svProductsMenu?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        svProductsMenu?.setOnQueryTextListener(this)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
