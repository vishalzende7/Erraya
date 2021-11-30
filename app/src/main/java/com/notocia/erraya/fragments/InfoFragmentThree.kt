package com.notocia.erraya.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.listeners.ButtonClickListener
import java.lang.ClassCastException

class InfoFragmentThree():Fragment() {

    lateinit var clickListener: ButtonClickListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_infoscreen_3, container, false)
        val nextButton: SynthButton = view.findViewById(R.id.buttonNext)
        nextButton.setOnClickListener{
            it.setTag(R.id.buttonNext,2)
            clickListener.onButtonClicked(it)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            clickListener = context as ButtonClickListener
        }
        catch (e: ClassCastException){
            e.printStackTrace()
        }
    }

}