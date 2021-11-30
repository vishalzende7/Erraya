package com.notocia.erraya.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import club.cred.synth.views.PitView
import club.cred.synth.views.SynthButton
import com.notocia.erraya.DatingModeActivity
import com.notocia.erraya.R
import com.notocia.erraya.adapters.DatingModeAdapter
import com.notocia.erraya.uicomponents.DatingMode


class DatingModeFragment:Fragment() {

    private lateinit var datingModeView:DatingMode
    private lateinit var nextButton:SynthButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dating_mode,container,false)
        datingModeView = view.findViewById(R.id.datingMode)
        nextButton = view.findViewById(R.id.buttonContinue)
        nextButton.setOnClickListener{
            (requireActivity() as DatingModeActivity).finishAndStartHomeActivity()
        }
        datingModeView.setAdapter(DatingModeAdapter(2) )
        return view
    }
}