package com.notocia.erraya

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.notocia.erraya.fragments.settings.SettingsFragment
import com.notocia.erraya.utils.Preference

class SettingsActivity:AppCompatActivity(), SettingsFragment.OnBackListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onBack() {

        onBackPressed()
    }

    fun onLogoutCalled(){
        Preference.getInstance(getPreferences(MODE_PRIVATE)).clearPreference()
        val i = Intent(this,MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }
}