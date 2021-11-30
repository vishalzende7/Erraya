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
import com.google.android.material.datepicker.MaterialDatePicker
import com.notocia.erraya.R
import com.notocia.erraya.utils.Profile
import java.text.SimpleDateFormat
import java.util.*

class BirthDateFragment: Fragment() {


    private lateinit var birthDate:EditText
    private lateinit var nextButton:SynthButton
    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Birth date")
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_birth_date,container,false)
        birthDate = view.findViewById(R.id.editTextBirthDate)
        nextButton = view.findViewById(R.id.buttonContinue)

        birthDate.setOnClickListener{
            datePicker.show(parentFragmentManager,"datePicker")
        }
        nextButton.setOnClickListener{
            if(birthDate.text.isNullOrBlank()){
                Toast.makeText(context, "Please enter birth date", Toast.LENGTH_SHORT).show()
            }
            else{
                Profile.getInstance().birthDate = birthDate.text.toString()
                findNavController().navigate(R.id.action_birthDateFragment_to_genderFragment)
            }
        }

        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("dd/M/yyyy")
            birthDate.setText(dateFormatter.format(Date(it)))
        }

        return view
    }
}