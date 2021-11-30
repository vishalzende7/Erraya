package com.notocia.erraya.uicomponents

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.flexbox.FlexboxLayout
import com.notocia.erraya.R
import com.notocia.erraya.models.BasicInfo

class InfoWidget(c: Context, attrs: AttributeSet) : ConstraintLayout(c, attrs) {
    private val widgetTitle: TextView
    private val flexBox: FlexboxLayout
    private val editInfoButton: ImageView
    private var mTitle: String? = "Info Widget"
    private var showIcon: Boolean = true
    private var mTintColor: Int = Color.BLACK

    init {
        inflate(context, R.layout.ui_component_basic_info_widget, this)
        widgetTitle = findViewById(R.id.widgetTitle)
        flexBox = findViewById(R.id.mFlexBox)
        editInfoButton = findViewById(R.id.editInfoButton)
        //flexBox.removeAllViews()

        context.obtainStyledAttributes(attrs, R.styleable.InfoWidget).apply {
            mTitle = getString(R.styleable.InfoWidget_title)
            showIcon = getBoolean(R.styleable.InfoWidget_showIcon, true)

            mTintColor = getColor(R.styleable.InfoWidget_iconTint, Color.BLACK)

            recycle()
        }
        if (mTitle != null) {
            widgetTitle.text = mTitle
        } else {
            widgetTitle.visibility = GONE
        }
        if (!showIcon)
            editInfoButton.visibility = GONE
    }

    fun addWidget(drawable: Int, text: String): View {
        val textView = TextView(context)
        val lp =
            MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
        lp.marginEnd = 35
        textView.alpha = 0.6f
        textView.text = text
        textView.compoundDrawablePadding = 15

        val d = AppCompatResources.getDrawable(context,drawable)
        if (d != null) {
            d.setTint(mTintColor)
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null)
        }

        flexBox.addView(textView, lp)
        return textView
    }

    fun addAllWidget(list: List<BasicInfo>) {
        removeAllWidget()

        list.forEach {
            val textView = TextView(context)
            val lp =
                MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)

            lp.marginEnd = 35
            textView.alpha = 0.6f
            textView.text = it.value
            textView.compoundDrawablePadding = 15
            val d = AppCompatResources.getDrawable(context,it.drawable)
            if (d != null) {
                d.setTint(mTintColor)
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null)
            }
            flexBox.addView(textView, lp)

        }
    }

    fun addAllWidgetWithoutIcon(list: List<BasicInfo>) {
        removeAllWidget()

        list.forEach {
            val textView = TextView(context)
            val lp =
                MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)

            lp.marginEnd = 35
            textView.alpha = 0.6f
            textView.text = it.value
            flexBox.addView(textView, lp)

        }
    }

    fun addPlainText(text: String): View {
        val textView = TextView(context)
        val lp =
            MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
        textView.alpha = 0.6f
        lp.marginEnd = 35
        textView.text = text
        flexBox.addView(textView, lp)
        return textView
    }

    fun removeAllWidget() {
        flexBox.removeAllViews()
    }
}