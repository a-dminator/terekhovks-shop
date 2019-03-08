package ru.napoleonit.terekhov_shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.product_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        GlobalScope.launch(Dispatchers.Main) {

            val categoriesDeferred = GlobalScope.async(Dispatchers.IO) {
                getCategories()
            }

            val categories = categoriesDeferred.await()

            categoriesListView.adapter = CategoriesAdapter(categories, this@CategoriesActivity)
        }
    }

    fun getCategories() = run {
        val categoriesJson = makeCategoriesRequest()
        getCategories(categoriesJson)
    }

    fun makeCategoriesRequest() =
        makeRequest("https://gist.githubusercontent.com/a-dminator/22993c39ab0d7a74c4b8f951945d9234/raw/bf8799c1ce387a91f204b494a9a50f208f70cca2/categories.json")

    fun makeRequest(url: String) = run {

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        val response = client.newCall(request).execute()

        response.body()!!.string()
    }

    fun getCategories(json: String) = Json.parse(Category.serializer().list, json)

}
