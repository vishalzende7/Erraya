package com.notocia.erraya.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.notocia.erraya.R
import com.notocia.erraya.adapters.MatchListAdapter.*
import com.notocia.erraya.models.MatchItem

class MatchListAdapter : Adapter<MatchHolder> {

    private var matchList: List<MatchItem>

    constructor(list: List<MatchItem>) : super() {
        this.matchList = list
    }

    override fun getItemViewType(position: Int): Int {
        if(matchList.size < 2 && position == 1)
            return 1
        else
            return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.item_match_list_default,parent,false)
        return MatchHolder(v)
    }

    override fun onBindViewHolder(holder: MatchHolder, position: Int) {
        val m = matchList[position]
        holder.profileName.text = m.profileName()
        if(position == 1){
            holder.blurFrame.visibility = View.VISIBLE
            holder.profileName.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return if(matchList.size < 2) 2 else matchList.size
    }

    fun updateList(list: List<MatchItem>) {
        this.matchList = list
        notifyDataSetChanged()
    }

    class MatchHolder(v:View): ViewHolder(v){
        var profileName:TextView = v.findViewById(R.id.profile_name)
        var blurFrame:FrameLayout = v.findViewById(R.id.premiumFeature)
    }
}