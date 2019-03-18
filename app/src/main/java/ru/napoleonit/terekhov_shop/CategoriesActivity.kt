package ru.napoleonit.terekhov_shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class CategoriesActivity : AppCompatActivity() {

    val requestMaker = getRequestMaker()
    val categoriesListUrl = "https://gist.githubusercontent.com/a-dminator/22993c39ab0d7a74c4b8f951945d9234/raw/bf8799c1ce387a91f204b494a9a50f208f70cca2/categories.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        requestMaker.make(
            url = categoriesListUrl,
            onResult = { categoriesJson ->
                val categories = getCategories(categoriesJson)
                categoriesListView.adapter = CategoriesAdapter(categories, this@CategoriesActivity)
            },
            onError = {

            }
        )
    }

    fun getCategories(json: String) = Json.parse(Category.serializer().list, json)
}
