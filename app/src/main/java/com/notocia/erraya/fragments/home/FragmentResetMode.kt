package com.notocia.erraya.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import club.cred.synth.views.SynthButton
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.R
import com.notocia.erraya.adapters.DatingModeAdapter

class FragmentResetMode:Fragment() {
    private lateinit var backButton: SynthImageButton
    private lateinit var continueButton: SynthButton
    private var list: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_reset_dating_mode,container,false)
        backButton = v.findViewById(R.id.buttonBack)
        backButton.setOnClickListener(clickListener)

        continueButton = v.findViewById(R.id.buttonContinue)
        continueButton.setOnClickListener(clickListener)

        list = v.findViewById(R.id.recyclerList)
        list?.adapter = DatingModeAdapter(2)
        return v
    }

    private val clickListener = View.OnClickListener{
        if(it is SynthButton){
            //Save and continue
            //TODO: Save and Continue
        }
        findNavController().popBackStack()
    }
}