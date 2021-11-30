package com.notocia.erraya

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import com.notocia.erraya.listeners.ButtonClickListener
import com.notocia.erraya.utils.Preference

class MainActivity : AppCompatActivity(), ButtonClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infohost)

        val pref = Preference.getInstance(this.getPreferences(MODE_PRIVATE))
        pref.loadPreference()
        if (pref.isUserLoggedIn) {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }

    }

    override fun onButtonClicked(v: View) {
        Log.i("MTTAG", v.getTag(R.id.buttonNext).toString())
        val which: Int = v.getTag(R.id.buttonNext).toString().toInt()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.info_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        when (which) {
            0 -> {
                Log.i("MTTAG", "Which is Zero")
                navController.navigate(R.id.action_infoFragmentOne_to_infoFragmentTwo)
            }
            1 -> {
                navController.navigate(R.id.action_infoFragmentTwo_to_infoFragmentThree)
            }
            2 -> {
                navController.navigate(R.id.action_infoFragmentThree_to_signupActivity)
            }
        }
    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.info_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MTTAG", "Finishing $this")
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            2
        )
    }
}