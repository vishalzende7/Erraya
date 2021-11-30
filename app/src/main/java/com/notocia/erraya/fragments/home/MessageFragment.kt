package com.notocia.erraya.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.ChattingActivity
import com.notocia.erraya.R
import com.notocia.erraya.adapters.MatchListAdapter
import com.notocia.erraya.adapters.MessageListAdapter
import com.notocia.erraya.models.MatchItem
import com.notocia.erraya.models.MessageListItem
import java.util.ArrayList

class MessageFragment:Fragment(), MessageListAdapter.TileClickListener {

    private lateinit var buttonPreference: SynthImageButton
    private lateinit var buttonMap: SynthImageButton
    private lateinit var buttonReset: SynthImageButton
    private lateinit var buttonNotification: SynthImageButton
    private lateinit var matchListDefault:RecyclerView
    private lateinit var msgListView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_message,container,false)

        buttonMap = v.findViewById(R.id.buttonMap)
        buttonPreference = v.findViewById(R.id.buttonPreference)
        buttonReset = v.findViewById(R.id.buttonReset)
        buttonNotification = v.findViewById(R.id.buttonNotification)
        matchListDefault = v.findViewById(R.id.matchListDefault)
        msgListView = v.findViewById(R.id.messageList)

        buttonMap.setOnClickListener(listener)
        buttonPreference.setOnClickListener(listener)
        buttonReset.setOnClickListener(listener)
        buttonNotification.setOnClickListener(listener)

        populateData()
        return v
    }

    private val listener = View.OnClickListener {
        val i: String = it.tag as String
        when (i) {
            "0" -> {
                findNavController().navigate(R.id.action_messageFragment_to_fragmentPreferences)
            }
            "1" -> {
                findNavController().navigate(R.id.action_messageFragment_to_fragmentMap)
            }
            "2" -> {
                findNavController().navigate(R.id.action_messageFragment_to_fragmentResetMode)
            }
            "3" -> {
                findNavController().navigate(R.id.action_messageFragment_to_fragmentNotification)
            }
        }
    }


    //TODO: can delete safely latter
    private fun populateData(){
        val list = ArrayList<MatchItem>()
        list.add(MatchItem("Maya",1,""))
        list.add(MatchItem("Tanya",2,""))
        list.add(MatchItem("Sristhi",3,""))
        list.add(MatchItem("Rohini",4,""))
        list.add(MatchItem("Rashi",5,""))

        val mList = ArrayList<MessageListItem>()
        mList.add(MessageListItem("Company Name","Lorem ipsum dolor sit amet",R.drawable.ic_logo))
        mList.add(MessageListItem("Maya","Lorem ipsum dolor sit amet",R.drawable.item_msg))
        mList.add(MessageListItem("Tanya","Lorem ipsum dolor sit amet",R.drawable.ic_placeholder))
        mList.add(MessageListItem("Rashi","Lorem ipsum dolor sit amet",R.drawable.ic_placeholder_2))
        mList.add(MessageListItem("Siddhi","Lorem ipsum dolor sit amet",R.drawable.item_msg))


        matchListDefault.adapter = MatchListAdapter(list)
        val messageListAdapter = MessageListAdapter(mList)
        messageListAdapter.setTileClickListener(this)
        msgListView.adapter = messageListAdapter
    }

    override fun onTileClickListener(index: Int, view: View, item: MessageListItem) {
        val bundle = Bundle()
        bundle.putInt("profileId",index)
        bundle.putInt("imageResource",item.imageResource)
        bundle.putString("profileName",item.profileTitle)
        bundle.putInt("profileType",1) //1 for normal profile and 0 for company profile
        val i = Intent(context,ChattingActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}