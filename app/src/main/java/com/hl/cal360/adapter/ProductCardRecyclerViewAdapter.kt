package com.hl.cal360.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hl.cal360.R
import com.hl.cal360.network.ImageRequester
import com.hl.cal360.network.ProductEntry

/**
 * Adapter used to show a simple grid of products.
 */
class ProductCardRecyclerViewAdapter(private val productList: List<ProductEntry>) : RecyclerView.Adapter<ProductCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.cal360_product_card, parent, false)
        return ProductCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ProductCardViewHolder, position: Int) {
        // TODO: Put ViewHolder binding code here in MDC-102
        if (position < productList.size) {
            val product = productList[position]
            holder.productTitle.text = product.title
            holder.productPrice.text = product.price
            ImageRequester.setImageFromUrl(holder.productImage, product.url)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    Toast.makeText(v.context, holder.productTitle.text, Toast.LENGTH_SHORT).show()

                }
            })
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
