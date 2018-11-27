package com.hl.cal360.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.hl.cal360.R
import com.hl.cal360.network.ShopCal


/**
 * Adapter used to show a simple grid of products.
 */
class ShopCalViewAdapter(context:Context, resource: Int,  calList: List<ShopCal>) : ArrayAdapter<ShopCal>(context,resource,calList) {
    var mContext: Context
    var mResource: Int
    // R.id.cal360_list_item_shopcal

    init{
        mContext = context
        mResource = resource
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val shopCalItem = getItem(position) as ShopCal
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(mResource, parent, false)
        }
        // Lookup view for data population
        val tvTitle = convertView!!.findViewById(R.id.txtTitle) as TextView
        val tvTime = convertView.findViewById(R.id.txtTime) as TextView
        val imgIcon = convertView.findViewById<View>(R.id.imgIcon) as ImageView
        imgIcon.setImageResource(R.drawable.cal360_logo)
        // Populate the data into the template view using the data object
        tvTitle.setText(shopCalItem.title)
        tvTime.setText(shopCalItem.timeFrom + " - " + shopCalItem.timeTo)
        val temp = position % 5 + 1


        // Return the completed view to render on screen
        return convertView
    }
}
