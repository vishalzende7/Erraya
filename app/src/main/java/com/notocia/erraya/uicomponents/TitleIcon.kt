package com.notocia.erraya.uicomponents

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.notocia.erraya.R

class TitleIcon(c: Context, attrs: AttributeSet?) : LinearLayout(c, attrs) {

    private var title: String? = "Drinking"



    private val optionCategoryName: TextView

    private var iListener:TitleTouchListener? = null


    constructor(c: Context) : this(c, null)

    private val mListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }

    private val mDetector = GestureDetector(context, mListener)

    private val mTouchListener = object : View.OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
            mDetector.onTouchEvent(event).let { result ->
                return if (!result) {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        iListener?.onTouchDown(p0)
                        true
                    } else false
                } else
                    true
            }
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.TitleIcon).apply {
            title =
                if (getString(R.styleable.TitleIcon_title) == null) "Drinking" else getString(
                    R.styleable.TitleIcon_title
                )
            recycle()
        }
        orientation = HORIZONTAL
        inflate(c, R.layout.ui_component_title_icon, this)

        optionCategoryName = findViewById(R.id.optionCategoryName)

        optionCategoryName.text = title

        setOnTouchListener(mTouchListener)
    }


        fun setClickListener(a:TitleTouchListener){
        iListener = a
    }


    interface TitleTouchListener{
        fun onTouchDown(v: View?)
    }

}