package com.hl.cal360.activity

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost
import com.hl.cal360.custom.DrawerSafeViewPager
import com.hl.cal360.fragment.CalendarFragment
import com.hl.cal360.fragment.LoginFragment
import com.hl.cal360.fragment.MaterialCalendarFragment
import com.hl.cal360.fragment.RegisterFragment
import java.util.*


class DashboardActivity : AppCompatActivity(), NavigationHost {

    //lateinit var mHelpLiveo: HelpLiveo
    lateinit var mDrawer: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var nvDrawer: NavigationView
    lateinit var drawerToggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cal360_dashboard_activity)

//        if (savedInstanceState == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.container, LoginFragment())
//                    .commit()
//        }
        toolbar = this!!.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
        mDrawer = this!!.findViewById(R.id.drawer_layout)
        drawerToggle = setupDrawerToggle();
//
//        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
       // mDrawer.on
//
        nvDrawer = this!!.findViewById(R.id.nvView)
//        // Setup drawer view
        setupDrawerContent(nvDrawer)


//        val adapter= FragmentPagerItemAdapter(
//                supportFragmentManager, FragmentPagerItems.with(this)
//                .add("Calendar", MaterialCalendarFragment::class.java)
//                .add("Week View", CalendarFragment::class.java)
//                .create())

        val viewPager=findViewById<View>(R.id.viewpager) as DrawerSafeViewPager
        setupViewPager(viewPager)
        //viewPager.adapter=adapter

//        val viewPagerTab=findViewById<View>(R.id.viewpagertab) as SmartTabLayout
        val viewPagerTab=findViewById<View>(R.id.viewpagertab) as TabLayout
        val inflater: LayoutInflater= LayoutInflater.from(viewPagerTab.context)
        val res: Resources= viewPagerTab.context.resources
        viewPagerTab.setupWithViewPager(viewPager)
        viewPagerTab.getTabAt(0)!!.setIcon(R.drawable.ic_delete_black_24dp)
        viewPagerTab.getTabAt(1)!!.setIcon(R.drawable.ic_drafts_black_24dp)


//        layout.setOnTouchListener( View.OnTouchListener() {
//
//            override fun onTouch(v: View, event: MotionEvent):Boolean {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    if (actionBar.isShowing()) {
//                        actionBar.hide();
//                    } else {
//                        actionBar.show();
//                    }
//                    return true;
//                } else return false;
//            }
//        });

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter=ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(RegisterFragment(), "REGISTER")
        adapter.addFragment(CalendarFragment(), "TWO")
        //adapter.addFragment(CalendarFragment(), "TWO")
        viewPager.adapter=adapter
    }


    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList=ArrayList<Fragment>()
        private val mFragmentTitleList=ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList.get(position)
        }
    }


    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }


    fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        var fragment: Fragment?=null
        val fragmentClass: Class<*>
        when (menuItem.getItemId()) {
            R.id.nav_first_fragment -> fragmentClass= LoginFragment::class.java
            R.id.nav_second_fragment -> fragmentClass= CalendarFragment::class.java
            R.id.nav_third_fragment -> fragmentClass= MaterialCalendarFragment::class.java
            else -> fragmentClass= LoginFragment::class.java
        }

        try {
            fragment=fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Insert the fragment by replacing any existing fragment
        //val fragmentManager=supportFragmentManager
        //supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment!!).commit()
        navigateTo(fragment!!,false)
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true)
        // Set action bar title
        //setTitle(menuItem.getTitle())
        setTitle("test")

//        val navigationView=R.id.nav_draw as NavigationView
//        // Inflate the header view at runtime
//        val headerLayout=navigationView.inflateHeaderView(R.layout.nav_header)
//        // We can now look up items within the header if needed
//        val ivHeaderPhoto=headerLayout.findViewById(R.id.imageView)

        // Close the navigation drawer
        mDrawer.closeDrawers()
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return ActionBarDrawerToggle(this, mDrawer, toolbar,
            R.string.cal360_drawer_open,
            R.string.cal360_drawer_close
        )
    }


    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        // original is  flContent;
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }



}
