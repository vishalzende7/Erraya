package com.notocia.erraya.fragments.signin

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.SignupActivity
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.utils.Preference
import com.notocia.erraya.utils.Profile
import org.json.JSONObject

class FragmentOtp : Fragment() {

    private val isProfileExist: Boolean = false
    private lateinit var otpTextField:TextView
    private lateinit var otpTimer:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)

        val editText: EditText = view.findViewById(R.id.editTextOtp)
        val nextButton: SynthButton = view.findViewById(R.id.buttonGetOtp)
        otpTextField = view.findViewById(R.id.textView6)
        otpTimer = view.findViewById(R.id.otpTimer)

        nextButton.setOnClickListener {
            if (editText.text.isNullOrBlank()) {
                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                val profile = Profile.getInstance()
                val otp = editText.text.toString()
                if (profile.mobile_verification_token.isNullOrEmpty())
                    throw NoSuchFieldException("verification_token is empty API error")

                ErrayaApi.getInstance()
                    .validateOtp(otp, profile.device_token, profile.mobile_verification_token!!)
                    .onEnqueue { call, response, throwable ->
                        if (throwable == null) {
                            if (response!!.isSuccessful) {
                                if (response.body() != null) {
                                    val resp = response.body()!!
                                    //if resp is true means use is exist else user is new
                                    if (resp.status) {
                                        //Status is true, Profile exist
                                        profile.profileType = Profile.ProfileType.EXIST
                                        profile.api_token = resp.token
                                        Log.i("MTTAG","API Token: ${profile.api_token}")
                                        val pref =
                                            Preference.getInstance(requireActivity().getPreferences(
                                                AppCompatActivity.MODE_PRIVATE))
                                        pref.isUserLoggedIn = true
                                        pref.savePreference()
                                    } else {
                                        //Status false, Profile is New
                                        profile.profileType = Profile.ProfileType.NEW
                                        profile.mobile_verification_token = resp.token
                                    }

                                    if (profile.profileType == Profile.ProfileType.NEW) {
                                        findNavController().navigate(R.id.action_fragmentOtp_to_emailFragment)
                                    } else {
                                        findNavController().navigate(R.id.action_fragmentOtp_to_fragmentLoader)
                                    }
                                } else {
                                    Log.i("MTTAG", "Empty response received from server")
                                }
                            } else {
                                //Response is not 200 due to Server error code like 404,500,502 etc...
                                val errString = response.errorBody()?.string()
                                if (!errString.isNullOrEmpty()) {
                                    try {
                                        val je = JSONObject(errString)
                                        val msg = je.getJSONObject("valData").getJSONArray("errors")
                                            .getJSONObject(0).getString("msg")
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        Log.i("MTTAG", "Response error ( $je )")
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        } else {
                            throwable.printStackTrace()
                            Log.i("MTTAG", "Exception ${throwable.message}")
                        }
                    }
            }
        }
        c.start()

        return view
    }

    companion object {
        @JvmStatic
        fun getInstance() = FragmentOtp()
    }

    val c = object : CountDownTimer(60000,1000){
        override fun onTick(millisUntilFinished: Long) {
            otpTimer.text = "00:${millisUntilFinished/1000}"
        }

        override fun onFinish() {
            otpTextField.text = "Didn't get the OTP?"
            otpTimer.text = "Resend"
        }
    }
}