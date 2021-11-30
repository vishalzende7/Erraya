package com.notocia.erraya.fragments.profiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.utils.Profile

class NameFragment: Fragment() {
    private lateinit var profileName:EditText
    private lateinit var nextButton:SynthButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_profile_enter_name,container,false)
        profileName = view.findViewById(R.id.editTextProfileName)
        nextButton = view.findViewById(R.id.buttonContinue)
        nextButton.setOnClickListener{
            if(profileName.text.isNullOrEmpty()){
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            else {
                Profile.getInstance().name = profileName.text.toString()
                findNavController().navigate(R.id.action_enterNameFragment_to_birthDateFragment)
            }
        }
        return view
    }
}