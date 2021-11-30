package com.notocia.erraya.uicomponents

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val Float.dp: Float
    get() {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)
    }

internal inline fun <T> View.dynamicAttr(
    initialValue: T,
    crossinline onChange: (T) -> Unit = {}
): ReadWriteProperty<Any?, T> {
    return object : ObservableProperty<T>(initialValue) {

        var initDone = false

        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            if (!initDone) {
                initDone = true
                return
            }
            onChange(newValue)
            invalidate()
            requestLayout()
        }
    }
}

internal inline fun View.withAttrs(set: AttributeSet?, attrs: IntArray, func: TypedArray.() -> Unit) {
    val a = context.theme.obtainStyledAttributes(set, attrs, 0, 0)
    try {
        a.func()
    } finally {
        a.recycle()
    }
}