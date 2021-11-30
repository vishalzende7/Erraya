package com.notocia.erraya.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.notocia.erraya.R
import com.notocia.erraya.SignupActivity
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.utils.Preference
import org.json.JSONObject

class FragmentLoader : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val v = inflater.inflate(R.layout.fragment_load_profile, container, false)
        startLoading()
        return v
    }

    private fun startLoading() {
        val pref =
            Preference.getInstance(requireActivity().getPreferences(AppCompatActivity.MODE_PRIVATE))
        val profile = pref.profile
        if (profile.api_token == null)
            throw IllegalStateException("API token can not be null")
        Log.i("MTTAG","${profile.api_token}")
        ErrayaApi.getInstance().getProfile(profile.api_token!!)
            .onEnqueue { call, response, throwable ->
                if (throwable == null) {
                    if (response!!.isSuccessful) {
                        if (response.body() != null) {
                            val body = response.body()!!
                            profile.name = body.profileData!!.name
                            profile.birthDate = body.profileData!!.dbo
                            profile.email = body.profileData!!.email
                            profile.gender = body.profileData!!.gender
                            pref.isUserLoggedIn = true
                            pref.savePreference()
                            (requireActivity() as SignupActivity).navigateToHome()
                            //Navigate to home activity
                        } else {
                            Log.i("MTTAG", " Response body null @ $this")
                        }
                    } else {
                        val errString = response.errorBody()?.string()
                        if (!errString.isNullOrEmpty()) {
                            try {
                                val je = JSONObject(errString)
                                Log.i("MTTAG","$je")
                                val msg = je.getJSONObject("valData").getString("msg")
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

    }

}