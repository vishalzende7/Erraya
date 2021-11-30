package com.notocia.erraya.fragments.profiles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import club.cred.synth.views.SynthButton
import com.notocia.erraya.R
import com.notocia.erraya.adapters.InterestAdapter
import com.notocia.erraya.services.ErrayaApi
import com.notocia.erraya.services.onEnqueue
import com.notocia.erraya.services.responses.AllInterest
import com.notocia.erraya.utils.Profile
import org.json.JSONObject

class InterestFragment : Fragment() {

    val addClick = object : InterestAdapter.InterestAddClickListener {
        override fun onAddClick(data: AllInterest.Data, action: Int) {
            if (action == 0)
                interestList.add(data.id)
            else {
                interestList.remove(data.id)
            }
        }
    }

    private lateinit var nextButton: SynthButton
    private lateinit var search: EditText
    private lateinit var recycler: RecyclerView
    private var adapter: InterestAdapter = InterestAdapter(addClick)
    private var interestList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_interest, container, false)
        nextButton = view.findViewById(R.id.buttonContinue)
        search = view.findViewById(R.id.etSearch)
        recycler = view.findViewById(R.id.interestRecycler)

        nextButton.setOnClickListener {
            try {
                if (interestList.size < 0) {
                    Toast.makeText(context,
                        "Please select at least one interest",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                ErrayaApi.getInstance().updateOnSignup("asdasd",
                    interestList,
                    Profile.getInstance().photos!!,
                    Profile.getInstance().api_token!!).onEnqueue { call, response, throwable ->
                    if (throwable == null) {
                        if (response!!.isSuccessful) {
                            findNavController().navigate(InterestFragmentDirections.actionInterestFragmentToDatingModeActivity())
                        } else {
                            val errString = response.errorBody()?.string()
                            if (!errString.isNullOrEmpty()) {
                                val je = JSONObject(errString)
                                val msg = je.getJSONObject("valData")
                                    .getString("message")
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                Log.i("MTTAG", "Response error ( $je )")
                            }
                        }
                    } else {
                        throw throwable
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        callApi()
        return view
    }

    private fun callApi() {
        ErrayaApi.getInstance().getAllInterest().onEnqueue { call, response, throwable ->
            if (throwable == null) {
                if (response!!.isSuccessful) {
                    val b = response.body()
                    if (b?.interest != null && b.status) {
                        Log.i("MTTAG", "${b.interest?.get(0)?.title}")
                        adapter.setData(b.interest!!)
                        recycler.adapter = adapter
                    } else {
                        Log.i("MTTAG", "Response body null")
                    }
                } else {
                    Log.i("MTTAG", "Response is failed")
                }
            } else {
                throwable.printStackTrace()
            }
        }
    }


}