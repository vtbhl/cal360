package com.hl.cal360.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.*
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost


class ShopActivity : AppCompatActivity(),NavigationHost {

    //lateinit var mHelpLiveo: HelpLiveo
    lateinit var toolbar: Toolbar
    lateinit var mToolbar: Toolbar
    lateinit var mAdapter: ArrayAdapter<*>
    lateinit var mListView: ListView
    lateinit var mEmptyView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cal360_shop_activity)

        val mToolbar = findViewById(R.id.toolbar) as Toolbar
        val mListView = findViewById<View>(R.id.list) as ListView
        val mEmptyView = findViewById(R.id.emptyView) as TextView
        setSupportActionBar(mToolbar)

        var arrayMonth = arrayOf ("January","Febuary","March","April","May","June","July","August","September","October","November","December")

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



//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.shop_container, RegisterFragment())
//                    .commit()
//        }
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
                mAdapter.getFilter().filter(newText)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

}
