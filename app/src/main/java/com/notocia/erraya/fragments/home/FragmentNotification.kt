package com.notocia.erraya.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.R

class FragmentNotification:Fragment() {

    private lateinit var backButton:SynthImageButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_notification,container,false)
        backButton = v.findViewById(R.id.buttonBack)
        backButton.setOnClickListener(clickListener)
        return v
    }

    private val clickListener = View.OnClickListener{
        findNavController().popBackStack()
    }
}