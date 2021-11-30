package com.notocia.erraya.uicomponents

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.notocia.erraya.R
import com.notocia.erraya.uicomponents.PreferenceDropdown.State

class PreferenceTitle(c: Context, attrs: AttributeSet?) : LinearLayout(c, attrs) {


    private var title: String? = "Drinking"
    private var optionIcon: Drawable? =
        AppCompatResources.getDrawable(c, R.drawable.ic_drinking_cat)

    private val optionIconHolder: ImageView
    private val optionCategoryName: TextView
    private val optionRightButton: ImageView
    private var iListener:TitleTouchListener? = null
    private var mState: State = State.COLLAPSED

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
        context.obtainStyledAttributes(attrs, R.styleable.PreferenceTitle).apply {
            title =
                if (getString(R.styleable.PreferenceTitle_title) == null) "Drinking" else getString(
                    R.styleable.PreferenceTitle_title
                )
            optionIcon = this.getDrawable(R.styleable.PreferenceTitle_icon)

            recycle()
        }
        orientation = HORIZONTAL
        inflate(c, R.layout.ui_component_dpd_title, this)
        optionIconHolder = findViewById(R.id.optionIcon)
        optionCategoryName = findViewById(R.id.optionCategoryName)
        optionRightButton = findViewById(R.id.optionRightButton)

        if (optionIcon != null)
            optionIconHolder.setImageDrawable(optionIcon)

        optionCategoryName.text = title
        if (mState == State.COLLAPSED) {
            optionRightButton.rotation = 270f
        } else if (mState == State.EXPANDED) {
            optionRightButton.rotation = 90f
        }

        setOnTouchListener(mTouchListener)
    }


    fun setState(s:State){
        mState = s
        if (mState == State.COLLAPSED) {
            optionRightButton.rotation = 270f
        } else if (mState == State.EXPANDED) {
            optionRightButton.rotation = 90f
        }
    }
    fun getState() = mState

    fun setClickListener(a:TitleTouchListener){
        iListener = a
    }


    interface TitleTouchListener{
        fun onTouchDown(v:View?)
    }
}