package ru.napoleonit.terekhov_shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class ProductsActivity : ProductsView, AppCompatActivity() {

    lateinit var presenter: ProductsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val productsUrl = intent.getStringExtra("productsUrl")
        presenter = ProductsPresenter(productsUrl, this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onAppear()
    }

    override fun displayProducts(products: List<Product>) {
        productsListView.adapter = ProductsAdapter(products, this)
        loadingView.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        presenter.onReturn()
    }

    override fun displayError() {
        longToast("Произошла ошибка!")
    }

    override fun showExitAlert() {
        alert("Вы действительно хотите выйти?") {
            yesButton { dialog ->
                super.onBackPressed()
            }
            noButton { dialog ->

            }
        }
    }
}