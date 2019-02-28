package ru.napoleonit.terekhov_shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.product_item.view.*
import kotlinx.coroutines.*

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        GlobalScope.launch(Dispatchers.Main) {

            val productsDeferred = GlobalScope.async(Dispatchers.IO) {

                delay(3000)

                val tomato = Product(
                    title = "Помидор",
                    imageUrl = "http://dom-eda.com/uploads/images/catalog/item/c6ebcf64ba/e87b941b85_500.jpg"
                )

                val potato = Product(
                    title = "Картошка",
                    imageUrl = "https://static7.depositphotos.com/1002351/792/i/450/depositphotos_7926477-stock-photo-new-potato.jpg"
                )

                val onion = Product(
                    title = "Лук",
                    imageUrl = "https://static7.depositphotos.com/1002351/792/i/450/depositphotos_7926477-stock-photo-new-potato.jpg"
                )

                listOf(tomato, potato, onion)
            }

            val products = productsDeferred.await()

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

class Product(
    val title: String,
    val imageUrl: String
)

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}
