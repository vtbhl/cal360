package com.hl.cal360.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost
import com.hl.cal360.activity.ShopActivity
import kotlinx.android.synthetic.main.cal360_login_fragment.*
import kotlinx.android.synthetic.main.cal360_login_fragment.view.*


/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.cal360_login_fragment, container, false)
        view.next_button.setOnClickListener {
            if (!isPasswordValid(password_edit_text.text!!)){
                password_text_input.error = getString(R.string.cal360_error_password)
            }else{
                password_text_input.error = null
            }
        }

        view.password_edit_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_edit_text.text!!)) {
                // Clear the error.
                password_text_input.error = null

            }
            false
        }

        view.next_button.setOnClickListener{
            if (!isPasswordValid(password_edit_text.text!!)) {
                password_text_input.error = getString(R.string.cal360_error_password)
                //intent.putExtra(INTENT_USER_ID, user.id)
                //(activity as NavigationHost).navigateTo(MaterialCalendarFragment(), false)
            } else {
                // Clear the error.
                password_text_input.error = null
                val intent = Intent(context, ShopActivity::class.java)
                activity?.startActivity(intent)
                activity?.finish()
                // Navigate to the next Fragment.
                //(activity as NavigationHost).navigateTo(ProductGridFragment(), false)
                //(activity as NavigationHost).navigateTo(ProductGridFragment(), false)
            }
        }

        view.cancel_button.setOnClickListener{
            (activity as NavigationHost).navigateTo(ProductGridFragment(), false)
//            (activity as NavigationHost).navigateTo(MaterialCalendarFragment(), false)
        }


        return view
        // Snippet from "Navigate to the next Fragment" section goes here
    }


    private fun isPasswordValid(text:Editable?):Boolean{
        return text != null && text.length > 8
        //TextView v = (TextView)getView().findViewById(R.id.password_edit_text);
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
}
