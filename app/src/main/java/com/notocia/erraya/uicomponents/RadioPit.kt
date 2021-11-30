package com.notocia.erraya.uicomponents

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.withClip
import androidx.core.graphics.withTranslation
import club.cred.synth.internals.BlurHelper
import com.notocia.erraya.R
import com.notocia.erraya.listeners.RadioStateListener

class RadioPit(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var mWidth: Float = 30f.dp
    private var mHeight: Float = 30f.dp

    private var pressedDepth: Float = 6f.dp
    private var cornerRadius: Float = 14f.dp

    private var radioPitAppearance: Int = 0
    private var isChecked: Boolean = false
    private val paint = Paint().apply { color = Color.WHITE }

    private var mListener: RadioStateListener? = null

    private val lightInnerShadow: BlurHelper = BlurHelper(
        blur = pressedDepth / 2,
        radius = cornerRadius,
        shadowColor = Color.WHITE,
        onlyBorder = true
    )
    private val darkInnerShadow: BlurHelper = BlurHelper(
        blur = pressedDepth,
        radius = cornerRadius,
        shadowColor = Color.parseColor("#3B415D26"),
        onlyBorder = true
    )
    private val softEdge: BlurHelper = BlurHelper(
        blur = 1.5f.dp,
        radius = cornerRadius,
        shadowColor = Color.parseColor("#3B415D26"),
        onlyBorder = true
    )

    init {
        context.obtainStyledAttributes(attrs, R.styleable.RadioPit).apply {
            radioPitAppearance = getResourceId(R.styleable.RadioPit_pit_appearance, -1)
            isChecked = getBoolean(R.styleable.RadioPit_isChecked, false)
            recycle()
        }
        if (radioPitAppearance != -1) {
            context.theme.obtainStyledAttributes(
                radioPitAppearance,
                R.styleable.RadioPit_Appearance
            ).apply {
                lightInnerShadow.shadowColor =
                    getColor(R.styleable.RadioPit_Appearance_light_InnerShadow, 0x000000)
                darkInnerShadow.shadowColor =
                    getColor(R.styleable.RadioPit_Appearance_dark_InnerShadow, 0x000000)
                paint.color = getColor(R.styleable.RadioPit_Appearance_active_color, 0xFFFFFF)
                recycle()
            }
        }
        darkInnerShadow.strokeWidth = pressedDepth
        lightInnerShadow.strokeWidth = pressedDepth
        softEdge.strokeWidth = 2f.dp

    }

    private val path = Path()
    private val rect = RectF()
    override fun onDraw(canvas: Canvas?) {

        val heightF = height.toFloat()
        val widthF = width.toFloat()
        val leftF = widthF / 2
        val topF = heightF / 2

        if (rect.width() != widthF || rect.height() != heightF) {
            rect.set(0f, 0f, widthF, heightF)
            path.reset()
            path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
        }
        canvas?.withClip(path) {

            val tx = -pressedDepth * 1.5f
            val ty = -pressedDepth * 1.1f
            withTranslation(tx, ty) {
                lightInnerShadow.draw(
                    canvas,
                    (heightF + pressedDepth * 1.5f).toInt(),
                    (widthF + pressedDepth * 1.5f).toInt()
                )
            }

            darkInnerShadow.draw(
                canvas,
                (heightF + pressedDepth * 1.5f).toInt(),
                (heightF + pressedDepth * 1.5f).toInt()
            )
        }

        if (isChecked)
            canvas?.drawCircle(leftF, topF, 8f.dp, paint)
    }

    private val touchListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }
    private val gestureDetector = GestureDetector(context, touchListener)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("MTTAG", "OKAY")
        return gestureDetector.onTouchEvent(event).let { result ->
            if (!result) {
                if (event?.action == MotionEvent.ACTION_UP) {
                    return performClick()
                } else
                    false
            } else
                return true
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        isChecked = !isChecked
        mListener?.onrRadioStateChanged(isChecked)
        invalidate()
        Log.i("MTTAG", "onClick Called $isChecked is state")
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val mw = MeasureSpec.makeMeasureSpec(
            mWidth.toInt() + paddingStart + paddingEnd,
            MeasureSpec.EXACTLY
        )
        val mh = MeasureSpec.makeMeasureSpec(
            mHeight.toInt() + paddingTop + paddingBottom,
            MeasureSpec.EXACTLY
        )
        setMeasuredDimension(mw, mh)
    }

    fun setOnStateListener(listener: RadioStateListener) {
        mListener = listener
    }
}