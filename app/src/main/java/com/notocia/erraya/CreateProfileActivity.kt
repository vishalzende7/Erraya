package com.notocia.erraya

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class CreateProfileActivity:AppCompatActivity() {

    private lateinit var navHostFragment:NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.create_profile_nav_host) as NavHostFragment
        Log.i("MTTAG","Starting $this")
    }

    fun finishAndStartNewStackToDatingMode(){
        val i = Intent(this,DatingModeActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

    override fun onBackPressed() {
        val controller = navHostFragment.navController
        if(!controller.navigateUp())
            super.onBackPressed()
    }
}