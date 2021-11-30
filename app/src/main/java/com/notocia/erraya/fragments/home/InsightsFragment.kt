package com.notocia.erraya.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.R
import java.lang.Exception

class InsightsFragment:Fragment() {

    private lateinit var buttonPreference: SynthImageButton
    private lateinit var buttonMap: SynthImageButton
    private lateinit var buttonReset: SynthImageButton
    private lateinit var buttonNotification: SynthImageButton
    private var scrollListener:NestedScrollView.OnScrollChangeListener? = null

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try{
//            scrollListener = context as NestedScrollView.OnScrollChangeListener
//        }
//        catch (e:Exception){
//            e.printStackTrace()
//            scrollListener = null
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_insights,container,false)

        buttonMap = v.findViewById(R.id.buttonMap)
        buttonPreference = v.findViewById(R.id.buttonPreference)
        buttonReset = v.findViewById(R.id.buttonReset)
        buttonNotification = v.findViewById(R.id.buttonNotification)

        buttonMap.setOnClickListener(listener)
        buttonPreference.setOnClickListener(listener)
        buttonReset.setOnClickListener(listener)
        buttonNotification.setOnClickListener(listener)
        if(scrollListener != null) {
            val nes: NestedScrollView = v.findViewById(R.id.scrollView)
            nes.setOnScrollChangeListener(scrollListener)
        }

        return v
    }

    val listener = View.OnClickListener {
        val i: String = it.tag as String
        when (i) {
            "0" -> {
                findNavController().navigate(R.id.action_insightsFragment_to_fragmentPreferences)
            }
            "1" -> {
                findNavController().navigate(R.id.action_insightsFragment_to_fragmentMap)
            }
            "2" -> {
                findNavController().navigate(R.id.action_insightsFragment_to_fragmentResetMode2)
            }
            "3" -> {
                findNavController().navigate(R.id.action_insightsFragment_to_fragmentNotification)
            }
        }
    }
}