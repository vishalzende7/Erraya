package com.notocia.erraya.uicomponents

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.notocia.erraya.R


class TestCheckBox(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private var rLeft = 0
    private var rTop = 0
    private var rRight = 0
    private var rBottom = 0
    private var aWidth = 0
    private var aHeight = 0

    private val minWidth = 90
    private val minHeight = 90
    private val minPadY = 16
    private val mPaint: Paint
    private val bgPaint: Paint

    private var bgColor: Int = Color.BLACK
    private var iconColor: Int = Color.WHITE
    private var checkedState: Boolean = false
    private val isChecked get() = getChecked()
    private val bitMap: Bitmap
    private var srcRect: Rect
    private lateinit var dstRect: Rect
    private val drw: Drawable

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.CheckBox, 0, 0)
            .apply {
                try {
                    bgColor = getColor(R.styleable.CheckBox_backgroundColor, Color.BLACK)
                    iconColor = getColor(R.styleable.CheckBox_iconColor, Color.WHITE)
                    bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        style = Paint.Style.FILL
                        color = bgColor
                    }
                    mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        style = Paint.Style.STROKE
                        strokeWidth = 4f
                        color = iconColor
                    }
                    drw = getDrawable(R.styleable.CheckBox_srcCompat)!!
                } finally {
                    recycle()
                }
            }


        drw.setTint(iconColor)
        bitMap = drw.toBitmap(drw.intrinsicWidth, drw.intrinsicHeight)
        srcRect = Rect(0, 0, bitMap.width, bitMap.height)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("MTTAG", "Status: $isChecked")

        if (!isChecked) {
            canvas?.apply {
                bgPaint.alpha = 1
                drawRect(
                    rLeft.toFloat(),
                    rTop.toFloat(),
                    rRight.toFloat(),
                    rBottom.toFloat(),
                    bgPaint
                )
            }
        } else {
            canvas?.apply {
                mPaint.apply {
                    color = Color.WHITE
                }
                //val p = Path()

                drawBitmap(bitMap, srcRect, dstRect, mPaint)
            }
        }
    }

    fun setChecked(Value: Boolean) {
        checkedState = Value
    }

    fun getChecked(): Boolean {
        return checkedState

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        aWidth = w;
        aHeight = h;

        rLeft = 0 + minPadY + paddingStart
        rTop = 0 + minPadY + paddingTop
        rRight = w - minPadY - paddingEnd
        rBottom = h - minPadY - paddingBottom
        dstRect = Rect(rLeft, rTop, rRight, rBottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Try for a width based on our minimum
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth + minWidth + minPadY
        val w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 1)

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        val minh: Int = paddingBottom + paddingTop + minPadY + suggestedMinimumHeight + minHeight
        val h: Int = View.resolveSizeAndState(
            minh,
            heightMeasureSpec,
            0
        )
        setMeasuredDimension(w, h)
    }

    private val myListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }

    private val detector: GestureDetector = GestureDetector(context, myListener)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event).let { result ->
            if (!result) {
                if (event?.action == MotionEvent.ACTION_UP) {
                    return performClick()
                } else false
            } else true
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        changeState()
        return true
    }
    private fun changeState() {
        setChecked(!getChecked())
        invalidate()
    }
}