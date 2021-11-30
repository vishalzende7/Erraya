package com.notocia.erraya.fragments.signin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.SignupActivity
import com.notocia.erraya.utils.Profile
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import org.json.JSONObject

class EmailFragment : Fragment() {
    private lateinit var mobileTextBox: EditText
    private val apiClient = ErrayaApi.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)
        val buttonNext: SynthButton = view.findViewById(R.id.buttonNext)
        mobileTextBox = view.findViewById(R.id.textBox)

        buttonNext.setTag(R.id.buttonNext, 0)

        buttonNext.setOnClickListener {
            if (validateEmail(mobileTextBox.text)) {
                ErrayaApi.getInstance().validateEmail(mobileTextBox.text.toString())
                    .onEnqueue { call, response, throwable ->
                        if (throwable == null) {
                            if (response!!.isSuccessful) {
                                Profile.getInstance().email = mobileTextBox.text.toString()
                                (requireActivity() as SignupActivity).navigateToCreateProfile()

                            } else {
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
                        }
                    }
            } else {
                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun validateEmail(email:CharSequence):Boolean{
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}