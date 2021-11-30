package com.notocia.erraya.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.notocia.erraya.R
import com.notocia.erraya.adapters.MessageListAdapter.Holder
import com.notocia.erraya.models.MessageListItem

class MessageListAdapter(msgList: List<MessageListItem>) : Adapter<Holder>() {

    class Holder(v: View) : ViewHolder(v) {
        val profileImage: ImageView = v.findViewById(R.id.profileLogo)
        val title: TextView = v.findViewById(R.id.profileName)
        val subTitle: TextView = v.findViewById(R.id.topMsg)
        val timeText: TextView = v.findViewById(R.id.msgTime)
    }

    private var mList: List<MessageListItem> = ArrayList()
    private var mListener: TileClickListener? = null

    init {
        mList = msgList
    }

    fun setTileClickListener(l: TileClickListener) {
        this.mListener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_message_list, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val l = mList[position]
        holder.profileImage.setImageResource(l.imageResource)
        holder.title.text = l.profileTitle
        holder.subTitle.text = l.profileSubTitle

        holder.itemView.setOnClickListener{
            mListener?.onTileClickListener(position,it, l)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface TileClickListener {
        fun onTileClickListener(index: Int, view: View,item:MessageListItem)
    }
}
