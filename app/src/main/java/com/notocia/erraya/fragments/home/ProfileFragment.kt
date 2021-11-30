package com.notocia.erraya.fragments.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthButton
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.R
import com.notocia.erraya.utils.Preference

class ProfileFragment : Fragment() {

    private lateinit var buttonPreference: SynthImageButton
    private lateinit var buttonNotification: SynthImageButton
    private lateinit var buttonEditProfile: SynthButton
    private lateinit var profileName:TextView
    private lateinit var pref:Preference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pref = Preference.getInstance(requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)


        buttonPreference = v.findViewById(R.id.buttonPreference)
        buttonNotification = v.findViewById(R.id.buttonNotification)
        buttonEditProfile = v.findViewById(R.id.buttonEditProfile)

        profileName = v.findViewById(R.id.profileName)
        buttonPreference.setOnClickListener(listener)
        buttonNotification.setOnClickListener(listener)
        buttonEditProfile.setOnClickListener(editProfileListener)

        profileName.text = pref.profile.name


        return v
    }

    val editProfileListener = View.OnClickListener {
        findNavController().navigate(R.id.action_profileFragment2_to_editProfileActivity2)
    }

    val listener = View.OnClickListener {
        val i: String = it.tag as String
        when (i) {
            "0" -> {
                findNavController().navigate(R.id.action_profileFragment2_to_settingsActivity)
            }
            "1" -> {

            }
        }
    }
}