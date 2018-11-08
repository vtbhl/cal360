package com.hl.cal360.fragment

import android.graphics.RectF
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.alamkanak.weekview.DateTimeInterpreter
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import com.hl.cal360.R
import com.hl.cal360.R.layout.cal360_calendar_fragment
import jp.co.recruit_mp.android.lightcalendarview.LightCalendarView
import kotlinx.android.synthetic.main.cal360_calendar_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment(), WeekView.EventClickListener,MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    lateinit var mWeekView: WeekView

    //var mMonthChangeListener: MonthLoader.MonthChangeListener=MonthLoader.MonthChangeListener { newYear, newMonth ->
        // Populate the week view with some events.

    //}

    fun getEvents(newYear:Int, newMonth:Int):MutableList<out WeekViewEvent>{
        val events=ArrayList<WeekViewEvent>()

        var startTime=Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 3)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        var endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR, 1)
        endTime.set(Calendar.MONTH, newMonth - 1)
        var event=WeekViewEvent(1, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_01)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 3)
        startTime.set(Calendar.MINUTE, 30)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.set(Calendar.HOUR_OF_DAY, 4)
        endTime.set(Calendar.MINUTE, 30)
        endTime.set(Calendar.MONTH, newMonth - 1)
        event=WeekViewEvent(10, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_02)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 4)
        startTime.set(Calendar.MINUTE, 20)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.set(Calendar.HOUR_OF_DAY, 5)
        endTime.set(Calendar.MINUTE, 0)
        event=WeekViewEvent(10, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_03)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 5)
        startTime.set(Calendar.MINUTE, 30)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 2)
        endTime.set(Calendar.MONTH, newMonth - 1)
        event=WeekViewEvent(2, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_02)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 5)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        startTime.add(Calendar.DATE, 1)
        endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        endTime.set(Calendar.MONTH, newMonth - 1)
        event=WeekViewEvent(3, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_03)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.DAY_OF_MONTH, 15)
        startTime.set(Calendar.HOUR_OF_DAY, 3)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        event=WeekViewEvent(4, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_04)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.DAY_OF_MONTH, 1)
        startTime.set(Calendar.HOUR_OF_DAY, 3)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        event=WeekViewEvent(5, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_01)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH))
        startTime.set(Calendar.HOUR_OF_DAY, 15)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        event=WeekViewEvent(5, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_02)
        events.add(event)

        //AllDay event
        startTime=Calendar.getInstance()
        startTime.set(Calendar.HOUR_OF_DAY, 0)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 23)
        event=WeekViewEvent(7, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_04)
        events.add(event)
        events.add(event)

        startTime=Calendar.getInstance()
        startTime.set(Calendar.DAY_OF_MONTH, 8)
        startTime.set(Calendar.HOUR_OF_DAY, 2)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.set(Calendar.DAY_OF_MONTH, 10)
        endTime.set(Calendar.HOUR_OF_DAY, 23)
        event=WeekViewEvent(8, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_03)
        events.add(event)

        // All day event until 00:00 next day
        startTime=Calendar.getInstance()
        startTime.set(Calendar.DAY_OF_MONTH, 10)
        startTime.set(Calendar.HOUR_OF_DAY, 0)
        startTime.set(Calendar.MINUTE, 0)
        startTime.set(Calendar.SECOND, 0)
        startTime.set(Calendar.MILLISECOND, 0)
        startTime.set(Calendar.MONTH, newMonth - 1)
        startTime.set(Calendar.YEAR, newYear)
        endTime=startTime.clone() as Calendar
        endTime.set(Calendar.DAY_OF_MONTH, 11)
        event=WeekViewEvent(8, getEventTitle(startTime), startTime, endTime)
        event.color=resources.getColor(R.color.event_color_01)
        events.add(event)

        return events
    }

    override fun onEmptyViewLongPress(time: Calendar) {
        Toast.makeText(context, "Empty view long pressed " + getEventTitle(time), Toast.LENGTH_SHORT).show()
    }

    override fun onEventLongPress(event: WeekViewEvent?, eventRect: RectF?) {
        Toast.makeText(context, "Long pressed event " + event?.getName(), Toast.LENGTH_SHORT).show()
    }

    override fun onMonthChange(newYear: Int, newMonth: Int): MutableList<out WeekViewEvent> {
        return getEvents(newYear, newMonth)
    }

    override fun onEventClick(event: WeekViewEvent?, eventRect: RectF?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(context, "Clicked " + event?.getName(), Toast.LENGTH_SHORT).show()
    }

    lateinit var calendarView: LightCalendarView
    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(cal360_calendar_fragment, container, false)
        //(activity as AppCompatActivity).setSupportActionBar(view.app_bar_cal)
       // calendar1(view)
        mWeekView = view.weekView
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);



        return view
    }

    private fun setupDateTimeInterpreter(shortDate: Boolean) {
        mWeekView.setDateTimeInterpreter(object : DateTimeInterpreter {
            override fun interpretDate(date: Calendar): String {
                val weekdayNameFormat=SimpleDateFormat("EEE", Locale.getDefault())
                var weekday=weekdayNameFormat.format(date.time)
                val format=SimpleDateFormat(" M/d", Locale.getDefault())

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday=weekday[0].toString()
                return weekday.toUpperCase() + format.format(date.time)
            }

            override fun interpretTime(hour: Int): String {
                return if (hour > 11) (hour - 12).toString() + " PM" else if (hour == 0) "12 AM" else hour.toString() + " AM"
            }
        })
    }

//
//    fun calendar1 (view:View){
//        calendarView = view.calendarView as LightCalendarView
//
//        calendarView.apply {
//            monthFrom=Calendar.getInstance().apply { set(Calendar.MONTH, 0) }.time
//            monthTo=Calendar.getInstance().apply { set(Calendar.MONTH, 11) }.time
//            monthCurrent=Calendar.getInstance().time
//        }
//
//        // set the calendar view callbacks
//        calendarView.setOnStateUpdatedListener(object : LightCalendarView.OnStateUpdatedListener {
//            override fun onDateSelected(date: Date) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//            override fun onMonthSelected(date: Date, monthView: MonthView)
//            {
//                view.app_bar_cal.apply {
//                    title=formatter.format(date)
//                }
//
//                // add accents to some days with 1 second delay (simulating I/O delay)
//                Handler().postDelayed({
//                    val cal=Calendar.getInstance()
//                    val dates=(1..31).filter { it % 2 == 0 }.map {
//                        cal.apply {
//                            set(monthView.month.year + 1900, monthView.month.month, it)
//                        }.time
//                    }
//                    val map=mutableMapOf<Date, Collection<Accent>>().apply {
//                        dates.forEach { date ->
//                            val accents=(0..date.date % 3).map { DotAccent(10f, key="${formatter.format(date)}-$it") }
//                            put(date, accents)
//                        }
//                    }
//                    monthView.setAccents(map)
//                }, 1000)
//
//                Log.i("CalendarFragment", "onMonthSelected: date = $date")
//            }
//        })
//    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater!!.inflate(R.menu.cal360_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    protected fun getEventTitle(time: Calendar): String {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH))
    }

    fun getWeekView(): WeekView {
        return mWeekView
    }
}
