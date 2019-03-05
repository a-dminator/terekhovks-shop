package ru.napoleonit.terekhov_shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_products.*
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

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        @Serializable
        class Product(
            val title: String,
            val imageUrl: String
        )

        class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        GlobalScope.launch(Dispatchers.Main) {

            val productsDeferred = GlobalScope.async(Dispatchers.IO) {

                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://gist.githubusercontent.com/a-dminator/22993c39ab0d7a74c4b8f951945d9234/raw/9d18a4fd9fcb327f1f0743b6e46eccf9e7bf39c6/products.json")
                    .build()

                val response = client.newCall(request).execute()

                response.body()!!.string()
            }

            val productsJson = productsDeferred.await()

            val products = Json.parse(Product.serializer().list, productsJson)

            productsListView.adapter = object : RecyclerView.Adapter<ProductViewHolder>() {

                override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int) = run {
                    val view = layoutInflater.inflate(
                        R.layout.product_item,
                        recyclerView,
                        false
                    )
                    ProductViewHolder(view)
                }

                override fun getItemCount() = products.size

                override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
                    val product = products.get(position)
                    holder.itemView.titleView.text = product.title
                    Picasso.get()
                        .load(product.imageUrl)
                        .into(holder.itemView.pictureView)
                }
            }

            loadingView.visibility = View.INVISIBLE
        }
    }

}