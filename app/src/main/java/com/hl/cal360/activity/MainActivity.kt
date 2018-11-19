package com.hl.cal360.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost
import com.hl.cal360.fragment.LoginFragment


class MainActivity : AppCompatActivity(),NavigationHost {

    //lateinit var mHelpLiveo: HelpLiveo
    lateinit var toolbar: Toolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cal360_main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_container, LoginFragment())
                    .commit()
        }
    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        // original is  flContent;
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

}
