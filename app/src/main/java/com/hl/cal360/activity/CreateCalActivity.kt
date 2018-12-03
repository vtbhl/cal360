package com.hl.cal360.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import com.hl.cal360.R
import com.hl.cal360.abstracts.NavigationHost
import com.hl.cal360.fragment.SublimePickerFragment
import kotlinx.android.synthetic.main.cal360_create_cal_activity.*
import java.text.DateFormat
import java.util.*


class CreateCalActivity : AppCompatActivity(),NavigationHost {

    val SS_DATE_PICKER_CHECKED = "saved.state.date.picker.checked"
    val SS_TIME_PICKER_CHECKED = "saved.state.time.picker.checked"
    val SS_RECURRENCE_PICKER_CHECKED = "saved.state.recurrence.picker.checked"
    val SS_ALLOW_DATE_RANGE_SELECTION = "saved.state.allow.date.range.selection"
    val SS_START_YEAR = "saved.state.start.year"
    val SS_START_MONTH = "saved.state.start.month"
    val SS_START_DAY = "saved.state.start.day"
    val SS_END_YEAR = "saved.state.end.year"
    val SS_END_MONTH = "saved.state.end.month"
    val SS_END_DAY = "saved.state.end.day"
    val SS_HOUR = "saved.state.hour"
    val SS_MINUTE = "saved.state.minute"
    val SS_RECURRENCE_OPTION = "saved.state.recurrence.option"
    val SS_RECURRENCE_RULE = "saved.state.recurrence.rule"
    val SS_INFO_VIEW_VISIBILITY = "saved.state.info.view.visibility"
    val SS_SCROLL_Y = "saved.state.scroll.y"
    val INVALID_VAL = -1

    lateinit var mToolbar: Toolbar
    lateinit var svMainContainer: ScrollView

    // Views to display the chosen Date, Time & Recurrence options
    lateinit var tvYear: TextView
    lateinit var tvMonth:TextView
    lateinit var tvDay:TextView
    lateinit var tvHour:TextView
    lateinit var tvMinute: TextView
    lateinit var tvRecurrenceOption:TextView
    lateinit var tvRecurrenceRule:TextView
    lateinit var tvStartDate: TextView
    lateinit var tvEndDate:TextView
    lateinit var rlDateTimeRecurrenceInfo: RelativeLayout
    lateinit var llDateHolder: LinearLayout
    lateinit var llDateRangeHolder:LinearLayout

    // Chosen values
    lateinit var mSelectedDate: SelectedDate
    var mHour: Int = 0
    var mMinute:Int = 0
    lateinit var mRecurrenceOption: String
    lateinit var mRecurrenceRule:String
    var mFragmentCallback: SublimePickerFragment.Callback = object : SublimePickerFragment.Callback {
        override fun onCancelled() {
            rlDateTimeRecurrenceInfo.setVisibility(View.GONE)
        }

        override fun onDateTimeRecurrenceSet(
            selectedDate: SelectedDate,
            hourOfDay: Int, minute: Int,
            recurrenceOption: SublimeRecurrencePicker.RecurrenceOption,
            recurrenceRule: String
        ) {

            mSelectedDate = selectedDate
            mHour = hourOfDay
            mMinute = minute
            mRecurrenceOption = if (recurrenceOption!= null) recurrenceOption.name  else  "n/a"
            mRecurrenceRule = if (recurrenceRule!=null)  recurrenceRule else "n/a"

            updateInfoView()

            svMainContainer.post(Runnable {
                svMainContainer.scrollTo(
                    svMainContainer.getScrollX(),
                    cbAllowDateRangeSelection.getBottom()
                )
            })
        }
    }

    private fun updateInfoView() {
        if (mSelectedDate != null) {
            if (mSelectedDate.getType() === SelectedDate.Type.SINGLE) {
                llDateRangeHolder.visibility = View.GONE
                llDateHolder.visibility = View.VISIBLE

                tvYear.text =
                    applyBoldStyle("YEAR: ")
                        .append(mSelectedDate.getStartDate().get(Calendar.YEAR).toString())

                tvMonth.text =
                    applyBoldStyle("MONTH: ")
                        .append(mSelectedDate.getStartDate().get(Calendar.MONTH).toString())

                tvDay.text =
                    applyBoldStyle("DAY: ")
                        .append(mSelectedDate.getStartDate().get(Calendar.DAY_OF_MONTH).toString())

            } else if (mSelectedDate.getType() === SelectedDate.Type.RANGE) {
                llDateHolder.visibility = View.GONE
                llDateRangeHolder.visibility = View.VISIBLE

                tvStartDate.text =
                    applyBoldStyle("START: ")
                        .append(DateFormat.getDateInstance().format(mSelectedDate.getStartDate().time))

                tvEndDate.text =
                    applyBoldStyle("END: ")
                        .append(DateFormat.getDateInstance().format(mSelectedDate.getEndDate().time))

            }
        }

        tvHour.text = applyBoldStyle("HOUR: ").append(mHour.toString())
        tvMinute.text = applyBoldStyle("MINUTE: ").append(mMinute.toString())

        tvRecurrenceOption.text =
            applyBoldStyle("RECURRENCE OPTION: ")
                .append(mRecurrenceOption)

        tvRecurrenceRule.text =
            applyBoldStyle("RECURRENCE RULE: ").append(
                mRecurrenceRule
            )


        rlDateTimeRecurrenceInfo.visibility = View.VISIBLE
    }

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

         var ivLaunchPicker =  findViewById(R.id.ivLaunchPicker) as ImageView
         var cbDatePicker =  findViewById(R.id.cbDatePicker) as CheckBox
         var cbTimePicker =  findViewById(R.id.cbTimePicker) as CheckBox
         var cbRecurrencePicker = findViewById(R.id.cbRecurrencePicker) as CheckBox
         var rbDatePicker = findViewById(R.id.rbDatePicker) as RadioButton
         var rbTimePicker = findViewById(R.id.rbTimePicker) as RadioButton
         var rbRecurrencePicker = findViewById(R.id.rbRecurrencePicker) as RadioButton
         var tvPickerToShow = findViewById(R.id.tvPickerToShow) as TextView
         var tvActivatedPickers = findViewById(R.id.tvActivatedPickers) as TextView
         svMainContainer = findViewById(R.id.svMainContainer) as ScrollView

         var cbAllowDateRangeSelection = findViewById(R.id.cbAllowDateRangeSelection) as CheckBox

         llDateHolder =  findViewById(R.id.llDateHolder) as LinearLayout
         llDateRangeHolder = findViewById(R.id.llDateRangeHolder) as LinearLayout

        // Initialize views to display the chosen Date, Time & Recurrence options
         tvYear = findViewById(R.id.tvYear) as TextView
         tvMonth =  findViewById(R.id.tvMonth)as TextView
         tvDay = findViewById(R.id.tvDay)as TextView

         tvStartDate = findViewById(R.id.tvStartDate)as TextView
         tvEndDate =  findViewById(R.id.tvEndDate)as TextView

         tvHour = findViewById(R.id.tvHour)as TextView
         tvMinute = findViewById(R.id.tvMinute)as TextView

         tvRecurrenceOption = findViewById(R.id.tvRecurrenceOption) as TextView
         tvRecurrenceRule =  findViewById(R.id.tvRecurrenceRule) as TextView

         rlDateTimeRecurrenceInfo= findViewById(R.id.rlDateTimeRecurrenceInfo) as RelativeLayout

         ivLaunchPicker.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
         ivLaunchPicker.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                // DialogFragment to host SublimePicker
                var  pickerFrag =  SublimePickerFragment()
                pickerFrag.setCallback(mFragmentCallback)

                // Options
                var optionsPair  = getOptions()

                if (!optionsPair.first) { // If options are not valid
                    Toast.makeText(
                        applicationContext, "No pickers activated",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Valid options
                var bundle =  Bundle()
                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");
            }
        });



        var pickerFrag = SublimePickerFragment()
        pickerFrag.setCallback(mFragmentCallback)

        cbDatePicker.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                rbDatePicker.visibility = if (cbDatePicker.isChecked())
                        View.VISIBLE else View.GONE
                onActivatedPickersChanged();
            }
        })

        // De/activates Time Picker
        cbTimePicker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                rbTimePicker.visibility = if (cbTimePicker.isChecked())
                        View.VISIBLE else View.GONE
                onActivatedPickersChanged()
            }
        })

        // De/activates Recurrence Picker
        cbRecurrencePicker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                rbRecurrencePicker.visibility =
                    if (cbRecurrencePicker.isChecked()) View.VISIBLE else View.GONE
                onActivatedPickersChanged()
            }
        })

        cbAllowDateRangeSelection.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

            }
        })

        dealWithSavedInstanceState(savedInstanceState)
    }

    fun dealWithSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) { // Default
            cbDatePicker.setChecked(true)
            cbTimePicker.setChecked(true)
            cbRecurrencePicker.setChecked(true)
            cbAllowDateRangeSelection.setChecked(false)

            rbDatePicker.setChecked(true);
        } else { // Restore
            cbDatePicker.setChecked(savedInstanceState.getBoolean(SS_DATE_PICKER_CHECKED))
            cbTimePicker.setChecked(savedInstanceState.getBoolean(SS_TIME_PICKER_CHECKED))
            cbRecurrencePicker
                    .setChecked(savedInstanceState.getBoolean(SS_RECURRENCE_PICKER_CHECKED))
            cbAllowDateRangeSelection
                    .setChecked(savedInstanceState.getBoolean(SS_ALLOW_DATE_RANGE_SELECTION))

            rbDatePicker.visibility = if(cbDatePicker.isChecked())
                    View.VISIBLE else View.GONE
            rbTimePicker.visibility =  if(cbTimePicker.isChecked())
                    View.VISIBLE else View.GONE
            rbRecurrencePicker.visibility = if(cbRecurrencePicker.isChecked())
                    View.VISIBLE else View.GONE

            onActivatedPickersChanged()

            if (savedInstanceState.getBoolean(SS_INFO_VIEW_VISIBILITY)) {
                var startYear = savedInstanceState.getInt(SS_START_YEAR)

                if (startYear != INVALID_VAL) {
                    var startCal = Calendar.getInstance()
                    startCal.set(startYear, savedInstanceState.getInt(SS_START_MONTH),
                            savedInstanceState.getInt(SS_START_DAY))

                    var endCal = Calendar.getInstance()
                    endCal.set(savedInstanceState.getInt(SS_END_YEAR),
                            savedInstanceState.getInt(SS_END_MONTH),
                            savedInstanceState.getInt(SS_END_DAY))
                    mSelectedDate =  SelectedDate(startCal, endCal)
                }

                mHour = savedInstanceState.getInt(SS_HOUR);
                mMinute = savedInstanceState.getInt(SS_MINUTE);
                mRecurrenceOption = savedInstanceState.getString(SS_RECURRENCE_OPTION)
                mRecurrenceRule = savedInstanceState.getString(SS_RECURRENCE_RULE)

                updateInfoView();
            }

            var scrollY = savedInstanceState.getInt(SS_SCROLL_Y)

            if (scrollY != 0) {
                svMainContainer.post(object: Runnable {
                    override fun run() {
                        svMainContainer.scrollTo(svMainContainer.getScrollX(),
                                scrollY)
                    }
                })
            }

            // Set callback
            var restoredFragment =
                    supportFragmentManager.findFragmentByTag("SUBLIME_PICKER") as SublimePickerFragment
            //if (restoredFragment != null) {
            restoredFragment.setCallback(mFragmentCallback)
            //}
        }
    }

    fun getOptions(): Pair<Boolean, SublimeOptions> {
        var options = SublimeOptions()
        var displayOptions: Int = 0

        if (cbDatePicker.isChecked()) {
            displayOptions = displayOptions or SublimeOptions.ACTIVATE_DATE_PICKER
        }

        if (cbTimePicker.isChecked()) {
            displayOptions  = displayOptions or  SublimeOptions.ACTIVATE_TIME_PICKER
        }

        if (cbRecurrencePicker.isChecked()) {
            displayOptions = displayOptions or SublimeOptions.ACTIVATE_RECURRENCE_PICKER
        }

        if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        } else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
        }

        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(cbAllowDateRangeSelection.isChecked());

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);
        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return  Pair(if (displayOptions != 0) true else false, options)
        //return null
    }


    fun onActivatedPickersChanged() {
        if (!cbDatePicker.isChecked()
                && !cbTimePicker.isChecked()
                && !cbRecurrencePicker.isChecked()) {

            // None of the pickers have been activated
            tvActivatedPickers.setText("Pickers to activate (choose at least one):");
            tvPickerToShow.setText("Picker to show on dialog creation: N/A");
        } else {
            // At least one picker is active
            tvActivatedPickers.setText("Pickers to activate:");
            tvPickerToShow.setText("Picker to show on dialog creation:");

            if ((rbDatePicker.isChecked() && rbDatePicker.getVisibility() != View.VISIBLE)
                    || (rbTimePicker.isChecked() && rbTimePicker.getVisibility() != View.VISIBLE)
                    || (rbRecurrencePicker.isChecked() && rbRecurrencePicker.getVisibility() != View.VISIBLE)) {
                if (rbDatePicker.getVisibility() == View.VISIBLE) {
                    rbDatePicker.setChecked(true);
                    return;
                }

                if (rbTimePicker.getVisibility() == View.VISIBLE) {
                    rbTimePicker.setChecked(true);
                    return;
                }

                if (rbRecurrencePicker.getVisibility() == View.VISIBLE) {
                    rbRecurrencePicker.setChecked(true);
                }
            }
        }
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
