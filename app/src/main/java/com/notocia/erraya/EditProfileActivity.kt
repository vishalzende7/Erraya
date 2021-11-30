package com.notocia.erraya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import club.cred.synth.views.SynthImageButton

class EditProfileActivity: AppCompatActivity(){

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var backButton: SynthImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        backButton = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            onBackPressed()
        }

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.edit_profile_host) as NavHostFragment
    }

    override fun onBackPressed() {
        val navController = navHostFragment.navController

        if(!navController.popBackStack()){
            super.onBackPressed()
        }
    }
}