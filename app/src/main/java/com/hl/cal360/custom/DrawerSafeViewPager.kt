package com.hl.cal360.custom

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class DrawerSafeViewPager : ViewPager {

    private var mDefaultGutterSize: Int=0
    private var mGutterSize: Int=0
    private var enable: Boolean=true
    //var menuDragging = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    internal fun init() {
        val density=context.resources.displayMetrics.density
        mDefaultGutterSize=(DEFAULT_GUTTER_SIZE * density).toInt()
        //this.enable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth=measuredWidth
        val maxGutterSize=measuredWidth / 10
        mGutterSize=Math.min(maxGutterSize, mDefaultGutterSize)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // Don't handle events within the left gutter. This fixes an issue with
        // ViewPager briefly intercepting the bezel swipe gesture within a DrawerLayout
        // and starting a drag movement, causing a visual stutter.
        return if (!this.enable || (ev.action != MotionEvent.ACTION_UP && ev.x < 200.0)) {
            false
        } else super.onTouchEvent(ev)

//        if (this.enable) {
//            return super.onTouchEvent(ev);
//        }
//
//        return false;
    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            if (ev.x < 200.0) {
//                return super.dispatchTouchEvent(ev)
//            } else {
//                menuDragging=true
//            }
//        } else if (ev.action == MotionEvent.ACTION_UP) {
//            menuDragging=false
//        }
//        return menuDragging
//
//    }


    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.enable) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    companion object {
        private val DEFAULT_GUTTER_SIZE=16 // dips
    }
}
