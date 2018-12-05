package com.hl.cal360.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost
import com.hl.cal360.fragment.SublimePickerFragment
import java.util.*


class CreateCalActivity : AppCompatActivity(),NavigationHost {


    lateinit var mToolbar: Toolbar
    lateinit var svMainContainer: ScrollView

    var year:Int = 0
    var month:Int = 0
    var day: Int = 0
    lateinit var gSelectedDate: SelectedDate



    // Applies a StyleSpan to the supplied text
    private fun applyBoldStyle(text: String): SpannableStringBuilder {
        val ss = SpannableStringBuilder(text)
        ss.setSpan(
            StyleSpan(Typeface.BOLD), 0, text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ss
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cal360_create_cal_activity)
        mToolbar = findViewById(R.id.create_cal_toolbar)
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel)
        mToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(mToolbar.context, ShopActivity::class.java)
                mToolbar.context.startActivity(intent)
                finish()
            }
        })

        var today = Date()
        day = today.day
        year = today.year
        month = today.month

        gSelectedDate = SelectedDate(Calendar.getInstance())

        var tvDate = findViewById(R.id.tvDate) as TextView
        setPicker(tvDate,"DATE")

        var tvTime = findViewById(R.id.tvTime) as TextView
        setPicker(tvTime,"TIME")
    }

    private fun setPicker(tView : TextView, option:String){

        var mFragmentCallbackLocal: SublimePickerFragment.Callback = object : SublimePickerFragment.Callback {
            override fun onCancelled() {
                //rlDateTimeRecurrenceInfo.setVisibility(View.GONE)
            }
            override fun onDateTimeRecurrenceSet(
                selectedDate: SelectedDate,
                hourOfDay: Int, minute: Int
                ,recurrenceOption: SublimeRecurrencePicker.RecurrenceOption,
                recurrenceRule: String
            ) {

                if (option == "DATE") {
                    year = selectedDate.startDate.get(Calendar.YEAR)


                    month  =selectedDate.startDate.get(Calendar.MONTH) + 1
                    applyBoldStyle("MONTH: ")
                        .append((selectedDate.startDate.get(Calendar.MONTH) + 1).toString()).toString()

                    day = selectedDate.startDate.get(Calendar.DAY_OF_MONTH)
                    applyBoldStyle("DAY: ")
                        .append(selectedDate.startDate.get(Calendar.DAY_OF_MONTH).toString()).toString()
                    gSelectedDate = selectedDate
                    tView.text = applyBoldStyle("DAY: ").append(day.toString()).toString() +
                            ", " + applyBoldStyle("MONTH: ").append(month.toString()).toString()+
                            ", "+  applyBoldStyle("YEAR: ").append(year.toString()).toString()
                }else{
                    tView.text = hourOfDay.toString() + ":" + minute
                }
            }
        }

        tView.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v: View) {
                var  pickerFrag =  SublimePickerFragment()
                pickerFrag.setCallback(mFragmentCallbackLocal)
                var options = SublimeOptions()
                if (option == "DATE") {

                    options.pickerToShow = SublimeOptions.Picker.DATE_PICKER
                    options.setDisplayOptions(SublimeOptions.ACTIVATE_TIME_PICKER  or SublimeOptions.ACTIVATE_DATE_PICKER or SublimeOptions.ACTIVATE_RECURRENCE_PICKER)
                }else{

                    options.pickerToShow = SublimeOptions.Picker.TIME_PICKER
                    options.setDisplayOptions(SublimeOptions.ACTIVATE_DATE_PICKER or SublimeOptions.ACTIVATE_TIME_PICKER or SublimeOptions.ACTIVATE_RECURRENCE_PICKER)
                    options.setDateParams(gSelectedDate)
                    //options.set
                }
                options.setRecurrenceParams(SublimeRecurrencePicker.RecurrenceOption.CUSTOM,"FREQ=WEEKLY;WKST=SU;BYDAY=WE")

                var bundle =  Bundle()
                bundle.putParcelable("SUBLIME_OPTIONS",options )
                pickerFrag.arguments = bundle
                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
                pickerFrag.show(supportFragmentManager, "SUBLIME_PICKER")
            }
        })

    }
    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        // original is  flContent;
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.create_cal_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cal360_shop_create_cal, menu)
        return true





    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.action_save -> {
                val intent = Intent(this, ShopActivity::class.java)
                this.startActivity(intent)
                (this as Activity).finish()
                return true
            }
        }
        return true
    }


}
