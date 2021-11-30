package com.notocia.erraya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.notocia.erraya.listeners.ButtonClickListener

class SignupActivity : AppCompatActivity(), ButtonClickListener {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup_host)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.signup_host) as NavHostFragment
        Log.i("MTTAG", this.toString())
    }

    override fun onButtonClicked(v: View) {

    }

    fun navigateToCreateProfile() {
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_emailFragment_to_createProfileActivity)
    }

    fun navigateToHome() {
        val i = Intent(this, HomeActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {
        val navController = navHostFragment.navController
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}