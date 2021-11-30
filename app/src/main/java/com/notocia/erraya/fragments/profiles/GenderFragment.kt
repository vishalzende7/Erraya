package com.notocia.erraya.fragments.profiles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import club.cred.synth.views.PitView
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.utils.Profile
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.uicomponents.ElevateView
import org.json.JSONObject

class GenderFragment : Fragment() {


    private var isFemaleSelected: Boolean = true
    private var gender = 2 //1 means male 2 means female

    private lateinit var nextButton: SynthButton
    private lateinit var femaleSelect: ConstraintLayout
    private lateinit var maleSelect: ConstraintLayout

    private lateinit var femalePitView: PitView
    private lateinit var femaleElevatedView: ElevateView

    private lateinit var malePitView: PitView
    private lateinit var maleElevatedView: ElevateView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gender, container, false)

        femaleSelect = view.findViewById(R.id.femaleLayout)
        maleSelect = view.findViewById(R.id.maleLayout)

        femalePitView = view.findViewById(R.id.femalePitView)
        femaleElevatedView = view.findViewById(R.id.femaleElevatedView)
        malePitView = view.findViewById(R.id.malePitView)
        maleElevatedView = view.findViewById(R.id.maleElevatedView)

        femaleSelect.setOnClickListener(selectListener)
        maleSelect.setOnClickListener(selectListener)
        nextButton = view.findViewById(R.id.buttonContinue)
        nextButton.setOnClickListener {
            Profile.getInstance().gender = gender
            ErrayaApi.getInstance().signup(Profile.getInstance()).onEnqueue { call, response, throwable ->
                if(throwable == null){
                    if(response!!.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            Profile.getInstance().api_token = body.token
                            findNavController().navigate(R.id.action_genderFragment_to_uploadPhotos)
                        }
                        else{
                            Log.i("MTTAG","Response body ins null API error")
                            Toast.makeText(context,"Debug: Response body ins null API error",Toast.LENGTH_LONG).show()
                        }

                    }
                    else{
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
                }
                else{
                    throwable.printStackTrace()
                }
            }
        }
        return view
    }

    private val selectListener = View.OnClickListener {
        Log.i("MTTAG", "View is $it")
        val value: Int = it.tag.toString().toInt()
        Log.i("MTTAG", "value is $value")
        when (value) {
            0 -> {
                toggleSelect(true)
            }
            1 -> {
                toggleSelect(false)
            }
        }
    }

    private fun toggleSelect(state: Boolean) {
        isFemaleSelected = state
        if (isFemaleSelected) {
            gender = 2
            malePitView.visibility = View.GONE
            maleElevatedView.visibility = View.VISIBLE

            femalePitView.visibility = View.VISIBLE
            femaleElevatedView.visibility = View.GONE
        } else {
            gender = 1
            malePitView.visibility = View.VISIBLE
            maleElevatedView.visibility = View.GONE

            femalePitView.visibility = View.GONE
            femaleElevatedView.visibility = View.VISIBLE
        }
    }
}