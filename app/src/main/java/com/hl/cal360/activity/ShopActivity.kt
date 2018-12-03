package com.hl.cal360.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost
import com.hl.cal360.adapter.ProductCardRecyclerViewAdapter
import com.hl.cal360.adapter.ProductGridItemDecoration
import com.hl.cal360.network.ProductEntry
import com.hl.cal360.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter


class ShopActivity : AppCompatActivity(),NavigationHost {

    //lateinit var mHelpLiveo: HelpLiveo
    //lateinit var toolbar: Toolbar
    lateinit var mToolbar: Toolbar
    lateinit var mAdapter: ArrayAdapter<*>
    lateinit var mListView: ListView
    lateinit var mEmptyView: TextView
    lateinit var arrayMonth : Array<String>
    lateinit var shopProductGrid : NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val view = inflater.inflate(R.layout.cal360_product_grid_fragment, container, false)
        setContentView(R.layout.cal360_shop_activity)

        mToolbar = findViewById(R.id.toolbar) as Toolbar
        mListView = findViewById<View>(R.id.list) as ListView
        mEmptyView = findViewById(R.id.emptyView) as TextView
        shopProductGrid = findViewById(R.id.shop_product_grid) as NestedScrollView

        setSupportActionBar(mToolbar)
        setProductGrid()
        setProductGridVertical()

//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.shop_container, RegisterFragment())
//                    .commit()
//        }
    }

    private fun setProductGrid(){
        val recyclerView = findViewById(R.id.shop_recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(applicationContext, 2, GridLayoutManager.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        recyclerView.layoutManager = gridLayoutManager
        val adapter = StaggeredProductCardRecyclerViewAdapter(
            ProductEntry.initProductEntryList(resources))
        recyclerView.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.cal360_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.cal360_product_grid_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))


    }


    private fun setProductGridVertical(){
        val recyclerView = findViewById(R.id.shop_recycler_view_2)  as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(GridLayoutManager(applicationContext, 2, GridLayoutManager.VERTICAL, false))
        val adapter = ProductCardRecyclerViewAdapter(
            ProductEntry.initProductEntryList(resources)
        )
        recyclerView.setAdapter(adapter)
        val largePadding = resources.getDimensionPixelSize(R.dimen.cal360_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.cal360_product_grid_spacing_small)
        recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
    }

    private fun setList(){
        arrayMonth = arrayOf ("January","February","March","April","May","June","July","August","September","October","November","December")
        mAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arrayMonth
        )
        mListView.adapter = mAdapter

        mListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(
                this,
                adapterView.getItemAtPosition(i).toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        mListView.emptyView = mEmptyView

    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        // original is  flContent;
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cal360_shop_toolbar, menu)

        val mSearch = menu.findItem(R.id.action_search)

        val mSearchView = mSearch.getActionView() as SearchView
        mSearchView.setQueryHint("Search")

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //if (shopProductGrid.visibility != View.GONE) {
                    shopProductGrid.visibility = View.GONE
                //}
                if (!newText.equals("")) {
                    setList()
                    mAdapter.getFilter().filter(newText)
                }

                //mListView?.visibility = View.INVISIBLE
                return true
            }
        })


        MenuItemCompat.setOnActionExpandListener(mSearch, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Reset view
//                mAdapter.clear()
                //arrayMonth = arrayOf ()
                //mAdapter.notifyDataSetChanged()
                //mListView.adapter = mAdapter
                shopProductGrid.visibility =View.VISIBLE
                mListView.emptyView = null
                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

}
