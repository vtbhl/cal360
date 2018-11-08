package com.hl.cal360.fragment

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator

import com.hl.cal360.R
import com.hl.cal360.adapter.ProductGridItemDecoration
import com.hl.cal360.custom.NavigationIconClickListener
import com.hl.cal360.network.ProductEntry
import com.hl.cal360.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.cal360_product_grid_fragment.view.*


class ProductGridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.cal360_product_grid_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
        view.app_bar.setNavigationOnClickListener( NavigationIconClickListener(
                activity!!,
                view.product_grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.cal360_branded_menu),
                ContextCompat.getDrawable(context!!, R.drawable.cal360_close_menu))
        )
        // Set up the RecyclerView
        view.recycler_view.setHasFixedSize(true)
        //view.recycler_view.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        //val adapter = ProductCardRecyclerViewAdapter(
        //        ProductEntry.initProductEntryList(resources))


        val gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        view.recycler_view.layoutManager = gridLayoutManager
        val adapter = StaggeredProductCardRecyclerViewAdapter(
                ProductEntry.initProductEntryList(resources))
        view.recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.cal360_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.cal360_product_grid_spacing_small)
        view.recycler_view.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.product_grid.background = context?.getDrawable(R.drawable.cal360_product_grid_background_shape)
        }




        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater!!.inflate(R.menu.cal360_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }



}
