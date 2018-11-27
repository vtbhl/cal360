
package com.hl.cal360.staggeredgridlayout


import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hl.cal360.R
import com.hl.cal360.activity.ShopCalActivity
import com.hl.cal360.network.ImageRequester
import com.hl.cal360.network.ProductEntry

/**
 * Adapter used to show an asymmetric grid of products, with 2 items in the first column, and 1
 * item in the second column, and so on.
 */
class StaggeredProductCardRecyclerViewAdapter(private val productList: List<ProductEntry>?) : RecyclerView.Adapter<StaggeredProductCardViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggeredProductCardViewHolder {
        var layoutId = R.layout.cal360_staggered_product_card_first
        if (viewType == 1) {
            layoutId = R.layout.cal360_staggered_product_card_second
        } else if (viewType == 2) {
            layoutId = R.layout.cal360_staggered_product_card_third
        }

        val layoutView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return StaggeredProductCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: StaggeredProductCardViewHolder, position: Int) {
        if (productList != null && position < productList.size) {
            val product = productList[position]
            holder.productTitle.text = product.title
            holder.productPrice.text = product.price
            ImageRequester.setImageFromUrl(holder.productImage, product.url)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val intent = Intent(v.context, ShopCalActivity::class.java)
                    v.context.startActivity(intent)
                    (v.context as Activity).finish()
                }
            })
        }


    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }
}
