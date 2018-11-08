package com.hl.cal360.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView
import com.hl.cal360.R


class ProductCardViewHolder(itemView: View) //TODO: Find and store views from itemView
    : RecyclerView.ViewHolder(itemView){
    val productImage: NetworkImageView = itemView.findViewById(R.id.product_image)
    val productTitle: TextView = itemView.findViewById(R.id.product_title)
    val productPrice: TextView = itemView.findViewById(R.id.product_price)
}
