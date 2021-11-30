package com.notocia.erraya.uicomponents

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.notocia.erraya.R

class ElevateView(c: Context, attrs: AttributeSet) : View(c, attrs) {
    private val DEFAULT_BLUR_RADIUS:Float = 20f
    private val DEFAULT_FILL_COLOR:Int = Color.parseColor("#1A1B1D")
    private val DEFAULT_SAHDOW_1_COLOR:Int = Color.parseColor("#0c0c0d")
    private val DEFAULT_SHADOW_2_COLOR:Int = Color.parseColor("#FFFFFF")

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mShadowPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var blurMaskFilter: BlurMaskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
    private var mRectf: RectF = RectF(99f, 99f, 350 - 15f, 200 - 15f)
    private var mRect: RectF = RectF(100f, 100f, 350f, 200f)
    private var mBlurRadius:Float = 20f
    private var fillColor:Int = DEFAULT_FILL_COLOR
    private var bottomRightShadow:Int = DEFAULT_SAHDOW_1_COLOR
    private var topLeftShadow:Int = DEFAULT_SHADOW_2_COLOR
    init {
        context.obtainStyledAttributes(attrs, R.styleable.ElevateView).apply {
            mBlurRadius = getDimension(R.styleable.ElevateView_blurRadius,DEFAULT_BLUR_RADIUS)
            fillColor = getColor(R.styleable.ElevateView_fillColor,DEFAULT_FILL_COLOR)
            bottomRightShadow = getColor(R.styleable.ElevateView_bottomRightShadow,DEFAULT_SAHDOW_1_COLOR)
            topLeftShadow = getColor(R.styleable.ElevateView_topLeftShadow,DEFAULT_SHADOW_2_COLOR)

            blurMaskFilter = BlurMaskFilter(mBlurRadius,BlurMaskFilter.Blur.NORMAL)
            mPaint.apply {
                color = fillColor
                setShadowLayer(mBlurRadius,8f,8f,bottomRightShadow)
            }
            mShadowPaint.apply {
                color = topLeftShadow
                style = Paint.Style.STROKE
                strokeWidth = 8f
                maskFilter = blurMaskFilter
            }
            recycle()
        }
    }

    var mStart = 0f
    var mTop = 0f
    var mHeight = 0f
    var mWidth = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mStart += paddingStart
        mTop += paddingTop
        mHeight = (height - paddingBottom).toFloat()
        mWidth = (width - paddingEnd).toFloat()
        mRectf.set(mStart-1f,mTop-1f,mWidth-15f,mHeight-15f)
        mRect.set(mStart,mTop,mWidth,mHeight)

        canvas?.apply {
            drawRoundRect(mRectf, 20f, 20f, mShadowPaint)
            drawRoundRect(mRect, 20f, 20f, mPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }
}