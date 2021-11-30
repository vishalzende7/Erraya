package com.notocia.erraya.adapters

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notocia.erraya.R
import com.notocia.erraya.services.responses.AllInterest

class InterestAdapter(private var listener: InterestAddClickListener?) :
    RecyclerView.Adapter<InterestAdapter.InterestHolder>() {

    private var list: ArrayList<AllInterest.Data> = ArrayList()

    class InterestHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView = v.findViewById(R.id.title)
        val addButton: ImageView = v.findViewById(R.id.select)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_interest_view, parent, false)
        return InterestHolder(v)
    }

    override fun onBindViewHolder(holder: InterestHolder, position: Int) {
        val l = list[position]
        holder.title.text = l.title
        holder.addButton.tag = "0"
        holder.addButton.setOnClickListener {
            val action = it.tag.toString().toInt()
            listener?.onAddClick(l, action)
            if (action == 0) {
                it.rotation = 45f
                it.tag = "1"
            } else {
                it.rotation = 0f
                it.tag = "0"
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(l: ArrayList<AllInterest.Data>) {
        list = l
        notifyDataSetChanged()
    }

    interface InterestAddClickListener {
        fun onAddClick(data: AllInterest.Data, action: Int)
    }
}