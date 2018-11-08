package com.hl.cal360.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import com.hl.cal360.R
import com.hl.cal360.R.layout.cal360_material_calendar_fragment
import com.hl.cal360.custom.NavigationIconClickListener
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.cal360_material_backdrop.view.*
import kotlinx.android.synthetic.main.cal360_material_calendar_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*







class MaterialCalendarFragment() : Fragment() , OnDateSelectedListener {

    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        val text=if (selected) formatter.format(date.getDate()) else "No Selection"
        Toast.makeText(activity!!.baseContext, text, Toast.LENGTH_SHORT).show()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(cal360_material_calendar_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(view.app_bar_mat)
        view.app_bar_mat.setNavigationOnClickListener(
            NavigationIconClickListener(
                activity!!,
                view.layoutContent,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.cal360_branded_menu),
                ContextCompat.getDrawable(context!!, R.drawable.cal360_close_menu))
        )
        val calendarView=view.calendarView
        //DrawerBuilder().withActivity(activity!!).build()
        //calendarView.setHeaderColor(R.color.colorAccent)
        //calendarView.setEvents(events)

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater!!.inflate(R.menu.cal360_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

}
