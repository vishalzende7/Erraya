package com.notocia.erraya.fragments.signin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.listeners.ButtonClickListener
import com.notocia.erraya.utils.Profile
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.uicomponents.TestCheckBox
import com.notocia.erraya.utils.Preference
import org.json.JSONObject


class MobileFragment : Fragment() {


    private var listener: ButtonClickListener? = null
    private lateinit var mobileTextBox: EditText
    private val apiClient = ErrayaApi.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ButtonClickListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val checkBox: TestCheckBox = view.findViewById(R.id.checkBox1)
        val buttonNext: SynthButton = view.findViewById(R.id.buttonNext)
        mobileTextBox = view.findViewById(R.id.textBox)

        buttonNext.setTag(R.id.buttonNext, 0)

        buttonNext.setOnClickListener {

            if (checkBox.getChecked()) {
                if (mobileTextBox.text.isNotEmpty()) {
                    val call = apiClient.checkPhoneExist(mobileTextBox.text.toString())
                    call.onEnqueue { c, response, throwable ->
                        if (throwable == null) {
                            //No error can continue, this implies response will never null but could be failed due to API error or incorrect api syntax
                            if (response!!.isSuccessful) {
                                //Request is successful, check if API response for constraint error like (invalid input by user)
                                val resp = response.body()
                                if (resp != null) {
                                    if (resp.status_code == -1) {
                                        val pref = Preference.getInstance(requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE))
                                        val profile = pref.profile
                                        profile.mobile = mobileTextBox.text.toString()
                                        if (!resp.mStatus)
                                            Profile.getInstance().profileType =
                                                Profile.ProfileType.NEW
                                        else
                                            Profile.getInstance().profileType =
                                                Profile.ProfileType.EXIST

                                        Profile.getInstance().mobile_verification_token =
                                            resp.verification_token

                                        this.findNavController()
                                            .navigate(R.id.action_mobileFragment_to_fragmentOtp)
                                    }
                                } else {
                                    Log.i("MTTAG", "Response body is null")
                                }

                            } else {
                                //Response is not successful due to Server error code like 404,500,502 etc...Now if user is exist then also failed
                                val errString = response.errorBody()?.string()
                                if (!errString.isNullOrEmpty()) {
                                    try {
                                        val je = JSONObject(errString)
                                        val msg = je.getJSONObject("valData").getJSONArray("errors")
                                            .getJSONObject(0).getString("msg")
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        Log.i("MTTAG", "Response ( $je )")
//                                        this.findNavController()
//                                            .navigate(R.id.action_mobileFragment_to_fragmentOtp)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }

                        } else {
                            //If NW error, or json parse error or any other exception like timeout etc...
                            throwable.printStackTrace()
                            Toast.makeText(context,
                                "error occurred please try again latter",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please check Terms and Conditions", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return view
    }

}




