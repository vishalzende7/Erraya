package com.notocia.erraya.fragments.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthImageButton
import com.notocia.erraya.MainActivity
import com.notocia.erraya.R
import com.notocia.erraya.SettingsActivity
import com.notocia.erraya.uicomponents.TitleIcon
import com.notocia.erraya.utils.Preference

class SettingsFragment:Fragment() {

    private lateinit var backButton: SynthImageButton
    private var listener:OnBackListener? = null
    private var actionDeleteAccount:TitleIcon? = null
    private lateinit var actionLogout:FrameLayout
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnBackListener)
            listener = context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_settings,container,false)
        backButton = v.findViewById(R.id.buttonBack)
        actionDeleteAccount = v.findViewById(R.id.actionDeleteAccount)
        backButton.setOnClickListener(clickListener)
        actionDeleteAccount?.setClickListener(actionClickListener)
        actionLogout = v.findViewById(R.id.actionLogout)
        actionLogout.setOnClickListener{
            onLogout()
        }
        return v
    }

    private val clickListener = View.OnClickListener{
        listener?.onBack()
    }

    interface OnBackListener{
        fun onBack()
    }

    private val actionClickListener = object : TitleIcon.TitleTouchListener{
        override fun onTouchDown(v: View?) {
            Log.i("MTTAG","Working")
            findNavController().navigate(R.id.action_settingsFragment_to_deleteAccountFragment)
        }
    }

    private fun onLogout(){
        (requireActivity() as SettingsActivity).onLogoutCalled()
    }

}