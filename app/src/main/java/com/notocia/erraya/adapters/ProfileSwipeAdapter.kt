package com.notocia.erraya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.notocia.erraya.R

class ProfileSwipeAdapter(
    private var list: List<String> = emptyList(),
    private var c: Context?,
    private val click: View.OnClickListener?,
) :
    RecyclerView.Adapter<ProfileSwipeAdapter.ProfileHolder>() {

    class ProfileHolder(v: View) : RecyclerView.ViewHolder(v) {
        val profileImg: ImageView = v.findViewById(R.id.profileImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        val inf = LayoutInflater.from(parent.context)
        return ProfileHolder(inf.inflate(R.layout.item_swipe_profile, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        if ((position % 2) == 0) {
            holder.profileImg.setImageResource(R.drawable.ic_placeholder_2)
        } else {
            holder.profileImg.setImageResource(R.drawable.ic_placeholder)
        }
        holder.itemView.setOnClickListener(click)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}