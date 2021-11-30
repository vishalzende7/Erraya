package com.notocia.erraya.uicomponents

import android.content.Context
import android.content.res.Resources
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class CircleTest(
    c: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(c, attrs, defStyleAttr) {


    private val paint = Paint()
    private var blurMaskFilter:BlurMaskFilter = BlurMaskFilter(10f.dp,BlurMaskFilter.Blur.NORMAL)
    init {
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.parseColor("#26272B"))
        val cx = width/2f
        val cy = height/2f

        //Draw Top-left Shadow
        paint.apply {
            color = Color.parseColor("#404E4B")
            maskFilter = blurMaskFilter
        }
        canvas?.drawCircle(cx-4f.dp,cy-4f.dp,11f.dp,paint)

        //Draw fill circle
        paint.apply {
            color = Color.parseColor("#EDAB94")
            style = Paint.Style.FILL
            setShadowLayer(10f.dp,4f.dp,3f.dp,Color.parseColor("#0D0F0F"))
            maskFilter = null
        }
        canvas?.drawCircle(cx,cy,11f.dp,paint)


    }


    private val Float.dp: Float
        get() {
            val displayMetrics = Resources.getSystem().displayMetrics
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                displayMetrics
            )
        }
}