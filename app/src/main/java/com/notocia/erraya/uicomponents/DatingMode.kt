package com.notocia.erraya.uicomponents

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.notocia.erraya.R
import com.notocia.erraya.adapters.DatingModeAdapter

class DatingMode: ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, int: Int) : super(context, attrs, int)

    private var list:RecyclerView? = null
    private var adapter:DatingModeAdapter? = null
    init {
        inflate(context, R.layout.ui_component_dating_mode,this)
        list = findViewById(R.id.recyclerList)
    }

    fun setAdapter(adapter: DatingModeAdapter){
        this.adapter = adapter
        list?.adapter = adapter
    }

    fun getAdapter():DatingModeAdapter?{
        return adapter
    }

    fun getRecyclerList():RecyclerView?{
        return list
    }
}