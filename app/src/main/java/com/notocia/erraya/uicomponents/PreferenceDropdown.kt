package com.notocia.erraya.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.notocia.erraya.R
import java.lang.Exception

class PreferenceDropdown(c:Context, attrs:AttributeSet):LinearLayout(c,attrs), PreferenceTitle.TitleTouchListener{
    enum class State(val mState: Int) {
        COLLAPSED(0),
        EXPANDED(1)
    }

    private var mState: State = State.COLLAPSED
    private var mCollapsedHeight = 0
    private var mExpandedHeight = 0
    init {
        context.obtainStyledAttributes(attrs, R.styleable.PreferenceDropdown).apply {
            val s = getInt(R.styleable.PreferenceDropdown_state, 0)
            mState = if (s == 0)
                State.COLLAPSED
            else
                State.EXPANDED

            recycle()
        }

        orientation = VERTICAL
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val child = getChildAt(0)
        if(child is PreferenceTitle){
            (child as PreferenceTitle).setClickListener(this)
        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(mState == State.EXPANDED) {
            val child = getChildAt(0)
            if(child is PreferenceTitle){
                child.setState(mState)
            }
            mExpandedHeight = MeasureSpec.getSize(heightMeasureSpec)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
        else{
            try {
                val child = getChildAt(0)
                if(child is PreferenceTitle){
                    child.setState(mState)
                }
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                mCollapsedHeight = child.measuredHeight
                val hmSpec = MeasureSpec.makeMeasureSpec(mCollapsedHeight, MeasureSpec.EXACTLY)
                setMeasuredDimension(widthMeasureSpec, hmSpec)
            }
            catch(e:Exception){
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }

    fun setState(s:State){
        mState = s
        requestLayout()
    }

    fun getState():State = mState

    override fun onTouchDown(v: View?) {
        val s = if(mState == State.COLLAPSED) State.EXPANDED else State.COLLAPSED
        setState(s)
    }
}