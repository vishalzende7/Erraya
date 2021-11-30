package com.notocia.erraya.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.notocia.erraya.R

class PreferenceOption(c: Context, attrs:AttributeSet):LinearLayout(c,attrs) {

    private val optionName:TextView
    private val optionSwitch:SwitchCompat

    private var title:String? = "Socially"
    private var refId:Int = 0
    private var isChecked = false
    init {
        inflate(c, R.layout.ui_component_dpd_option,this)
        orientation = HORIZONTAL
        optionName = findViewById(R.id.optionName)
        optionSwitch = findViewById(R.id.optionSwitch)

        context.obtainStyledAttributes(attrs,R.styleable.PreferenceOption).apply {
            title = getString(R.styleable.PreferenceOption_title)
            refId = getInt(R.styleable.PreferenceOption_refId,0)
            isChecked = getBoolean(R.styleable.PreferenceOption_isChecked, false)
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        optionName.text = title
        optionSwitch.isChecked = isChecked
    }

}