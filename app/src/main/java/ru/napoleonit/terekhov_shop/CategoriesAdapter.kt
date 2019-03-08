package ru.napoleonit.terekhov_shop

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class CategoriesAdapter(
    val categories: List<Category>,
    val context: Context
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int) = run {
        val view = context.layoutInflater.inflate(
            R.layout.product_item,
            recyclerView,
            false
        )
        ViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.itemView.titleView.text = category.name
        Picasso.get()
            .load(category.imageUrl)
            .into(holder.itemView.pictureView)
        holder.itemView.onClick {
            context.startActivity<ProductsActivity>("productsUrl" to category.productsUrl)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}