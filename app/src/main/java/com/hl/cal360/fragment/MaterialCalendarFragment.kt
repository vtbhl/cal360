package com.hl.cal360.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hl.cal360.R
import com.hl.cal360.R.layout.cal360_material_calendar_fragment
import com.hl.cal360.adapter.ShopCalViewAdapter
import com.hl.cal360.network.ShopCal
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.cal360_material_calendar_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*







class MaterialCalendarFragment() : Fragment() , OnDateSelectedListener {

    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    private lateinit var mAdapter: ArrayAdapter<*>
    //lateinit var  scheduleList : ListView
    private lateinit var arrayMonth : Array<String>

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
        // inflate --> ok to call, other must findViewByID
        val view = inflater.inflate(cal360_material_calendar_fragment, container, false)
//        (activity as AppCompatActivity).setSupportActionBar(view.app_bar_mat)
//        view.app_bar_mat.setNavigationOnClickListener(
//            NavigationIconClickListener(
//                activity!!,
//                view.layoutContent,
//                AccelerateDecelerateInterpolator(),
//                ContextCompat.getDrawable(context!!, R.drawable.cal360_branded_menu),
//                ContextCompat.getDrawable(context!!, R.drawable.cal360_close_menu))
//        )
        //val calendarView=view.calendarView
        //DrawerBuilder().withActivity(activity!!).build()
        //calendarView.setHeaderColor(R.color.colorAccent)
        //calendarView.setEvents(events)

        //scheduleList = view.findViewById(R.id.scheduleList) as ListView
        arrayMonth = arrayOf ("January","February","March","April","May","June","July","August","September","October","November","December","Thang 13","Thang 14","Thang 15")
//        mAdapter = ArrayAdapter(
//            view.context,
//            android.R.layout.simple_list_item_1,
//            arrayMonth
//        )

        var shopCalList = LinkedList<ShopCal>()
        for ( i in 0..10){
            var shopCal1 =  ShopCal("Hoc bai " + i,"15:00","15:30")
            shopCalList.add(shopCal1)
        }

        mAdapter = ShopCalViewAdapter(view.context, R.layout.cal360_item_list_shopcal,shopCalList)

        view.scheduleList.adapter = mAdapter


//        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
//        view.app_bar.setNavigationOnClickListener( NavigationIconClickListener(
//            activity!!,
//            view.product_grid,
//            AccelerateDecelerateInterpolator(),
//            ContextCompat.getDrawable(context!!, R.drawable.cal360_branded_menu),
//            ContextCompat.getDrawable(context!!, R.drawable.cal360_close_menu))
//        )

        //view.scheduleList.setOnScrollListener(
        view.scheduleList.setOnScrollChangeListener(object:View.OnScrollChangeListener{
            override fun onScrollChange(p0: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                //scroll down
                if (oldScrollY > scrollY){
                    view.calendarView.animate().translationY(100.0f-view.calendarView.height).withLayer()
                    view.scheduleList.animate().translationY(120.0f-view.calendarView.height).withLayer()

                }
                if (oldScrollY <  scrollY){
                    view.calendarView.animate().translationY(0.0f ).withLayer()
                    view.scheduleList.animate().translationY(0.0f ).withLayer()
                }
            }
        })

//        scheduleList.setOnTouchListener( object: View.OnTouchListener {
//            override fun onTouch(v: View, event: MotionEvent):Boolean {
//                var downX = 0.0f
//                var lastMoveX = 0.0f
//                if (event.action == MotionEvent.ACTION_DOWN) {
//                    downX = event.getRawX()
//                    lastMoveX = downX
//                    calendarView.animate().translationY(100.0f-calendarView.height).withLayer()
//                    scheduleList.animate().translationY(120.0f-calendarView.height).withLayer()
//                    return true
//                }
//                if (event.action == MotionEvent.ACTION_HOVER_ENTER){
//                    lastMoveX = downX
//                    return true
//                }
//                if (event.action == MotionEvent.ACTION_MOVE) {
//                    val moveX = event.getRawX()
//                    val xDiff = Math.abs(moveX - downX)
//                    lastMoveX = moveX
//                    if (xDiff < 0.0f){
//                        calendarView.animate().translationY(100.0f-calendarView.height).withLayer()
//                        scheduleList.animate().translationY(120.0f-calendarView.height).withLayer()
//                    }
//                    else{
//                        calendarView.animate().translationY(0.0f + calendarView.height).withLayer()
//                        scheduleList.animate().translationY(10.0f + calendarView.height).withLayer()
//                    }
//
//                    return true
//                }
//
//
//                return false
//            }
//        })

        return view
    }


//    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
//        menuInflater!!.inflate(R.menu.cal360_toolbar_menu, menu)
//        super.onCreateOptionsMenu(menu, menuInflater)
//    }



}
