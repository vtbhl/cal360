
package com.hl.cal360.staggeredgridlayout

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView
import com.hl.cal360.R


class StaggeredProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var productImage: NetworkImageView = itemView.findViewById(R.id.product_image)
    var productTitle: TextView = itemView.findViewById(R.id.product_title)
    var productPrice: TextView = itemView.findViewById(R.id.product_price)
}
