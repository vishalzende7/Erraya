package com.notocia.erraya.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.notocia.erraya.R

class DatingModeAdapter(private val list:Int) :
    Adapter<DatingModeAdapter.ModeViewHolder>() {

    class ModeViewHolder(v:View): ViewHolder(v){
        init {

        }
    }
    init {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_dating_mode,parent,false)
        return ModeViewHolder(v)
    }

    override fun onBindViewHolder(holder: ModeViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return list
    }
}