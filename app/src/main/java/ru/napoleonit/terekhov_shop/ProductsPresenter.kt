package ru.napoleonit.terekhov_shop

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class ProductsPresenter(
    val productsUrl: String,
    val view: ProductsView,
    context: Context
) {
    val requestMaker = getRequestMaker(context)

    fun onAppear() {
        requestMaker.make(
            productsUrl,
            onResult = { productsJson ->
                val products = Json.parse(Product.serializer().list, productsJson)
                view.displayProducts(products)
            },
            onError = {
                view.displayError()
            }
        )
    }

    fun onReturn() {
        view.showExitAlert()
    }
}

interface ProductsView {
    fun displayProducts(products: List<Product>)
    fun showExitAlert()
    fun displayError()
}