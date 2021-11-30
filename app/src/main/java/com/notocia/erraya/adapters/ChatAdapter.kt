package com.notocia.erraya.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notocia.erraya.R
import com.notocia.erraya.models.ChatMessage

class ChatAdapter(list: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.Holder>() {
    private var list: List<ChatMessage> = ArrayList()

    init {
        this.list = list
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        var messageText: TextView = v.findViewById(R.id.chatText)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].messageAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        parent.clipChildren = false
        if (viewType == 0) {
            //Message Received template
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message_received, parent, false)
            return Holder(v)
        } else {
            //Message sent
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message_sent, parent, false)
            return Holder(v)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.messageText.text = list[position].message
    }

    override fun getItemCount(): Int {
        return list.size
    }
}