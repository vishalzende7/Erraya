package com.notocia.erraya.uicomponents

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.R

@SuppressLint("ClickableViewAccessibility")
class BottomNav(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val wrapperZero: FrameLayout
    private val wrapperOne: FrameLayout
    private val wrapperTwo: FrameLayout
    private val wrapperThree: FrameLayout

    private val tabZero: SynthImageButton
    private val tabOne: SynthImageButton
    private val tabTwo: SynthImageButton
    private val tabThree: SynthImageButton


    private var activeTab: Int = 0
    private var activeColor: Int = Color.parseColor("#EDAB94")
    private var listener: NavigationTabSelectListener? = null
    private var defaultColor: Int = Color.parseColor("#ffffff")


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
                        onClick(p0)
                        true
                    } else false
                } else
                    true
            }
        }
    }

    init {
        inflate(context, R.layout.ui_components_bottom_nav, this)

        this.clipChildren = false

        wrapperZero = findViewById(R.id.wrapperHome)
        wrapperOne = findViewById(R.id.wrapperMessage)
        wrapperTwo = findViewById(R.id.wrapperInsights)
        wrapperThree = findViewById(R.id.wrapperProfile)

        tabZero = findViewById(R.id.buttonHome)
        tabOne = findViewById(R.id.buttonMessage)
        tabTwo = findViewById(R.id.buttonInsights)
        tabThree = findViewById(R.id.buttonProfile)

        wrapperZero.setOnTouchListener(mTouchListener)
        wrapperOne.setOnTouchListener(mTouchListener)
        wrapperTwo.setOnTouchListener(mTouchListener)
        wrapperThree.setOnTouchListener(mTouchListener)

        context.theme.obtainStyledAttributes(attrs, R.styleable.BottomNav, 0, 0)
            .apply {
                activeTab = getInt(R.styleable.BottomNav_activeTab, 0)
                activeColor = getInt(R.styleable.BottomNav_activeTint, Color.parseColor("#EDAB94"))
                defaultColor =
                    getInt(R.styleable.BottomNav_colorOnPrimary, Color.parseColor("#FFFFFF"))
            }

        setActive(activeTab)
    }

    private fun setActive(index: Int) {
        when (index) {
            0 -> {
                activeTab = 0
                tabZero.drawable.setTint(activeColor)

                tabZero.isPressed = true

                tabOne.isPressed = false
                tabTwo.isPressed = false
                tabThree.isPressed = false

                tabOne.drawable.setTint(defaultColor)
                tabTwo.drawable.setTint(defaultColor)
                tabThree.drawable.setTint(defaultColor)
            }
            1 -> {
                activeTab = 1
                tabOne.drawable.setTint(activeColor)
                tabOne.isPressed = true

                tabZero.isPressed = false
                tabTwo.isPressed = false
                tabThree.isPressed = false

                tabZero.drawable.setTint(defaultColor)
                tabTwo.drawable.setTint(defaultColor)
                tabThree.drawable.setTint(defaultColor)
            }
            2 -> {
                activeTab = 2
                tabTwo.drawable.setTint(activeColor)
                tabTwo.isPressed = true

                tabZero.isPressed = false
                tabOne.isPressed = false
                tabThree.isPressed = false

                tabZero.drawable.setTint(defaultColor)
                tabOne.drawable.setTint(defaultColor)
                tabThree.drawable.setTint(defaultColor)
            }
            3 -> {
                activeTab = 3
                tabThree.drawable.setTint(activeColor)
                tabThree.isPressed = true

                tabZero.isPressed = false
                tabOne.isPressed = false
                tabTwo.isPressed = false

                tabZero.drawable.setTint(defaultColor)
                tabOne.drawable.setTint(defaultColor)
                tabTwo.drawable.setTint(defaultColor)
            }
        }
    }


    fun getActiveTab(): Int {
        return activeTab
    }

    fun setOnSelectListener(listener: NavigationTabSelectListener) {
        this.listener = listener
    }

    fun onClick(p0: View?) {
        Log.i("MTTAG", p0.toString())
        when (p0) {
            wrapperZero -> {
                setActive(0)
                listener?.onSelectTab(0, tabZero)
            }
            wrapperOne -> {
                setActive(1)
                listener?.onSelectTab(1, tabOne)
            }
            wrapperTwo -> {
                setActive(2)
                listener?.onSelectTab(2, tabTwo)
            }
            wrapperThree -> {
                setActive(3)
                listener?.onSelectTab(3, tabThree)
            }
        }
    }

    fun invalidateOnStartCycle(i:Int){
        setActive(i)
    }
    public interface NavigationTabSelectListener {
        fun onSelectTab(index: Int, v: View)
    }

}