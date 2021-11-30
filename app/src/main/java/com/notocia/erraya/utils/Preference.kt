package com.notocia.erraya.utils

import android.content.SharedPreferences
import android.util.Log

class Preference(private var pref: SharedPreferences) {

    var isUserLoggedIn = false
    var profileId: String? = ""
    var profile = Profile.getInstance()

    fun savePreference() {
        val editor = pref.edit()
        editor.putBoolean("user_status",isUserLoggedIn)
        editor.putString("name",profile.name)
        editor.putString("email",profile.email)
        editor.putInt("gender",profile.gender)
        editor.putString("birth_date",profile.birthDate)
        editor.putString("api_token",profile.api_token)
        editor.putString("device_token",profile.device_token)
        editor.putString("mobile",profile.mobile)
        editor.putString("profile_id",profileId)
        editor.apply()
    }

    fun clearPreference(){

        isUserLoggedIn = false
        pref.edit().clear().apply()
        profileId = "0"
    }
    fun loadPreference() {
        isUserLoggedIn = pref.getBoolean("user_status", false)
        Log.i("MTTAG","isUser Logged in $isUserLoggedIn")
        if (isUserLoggedIn){
            profile.name = pref.getString("name","No Name")
            profile.email = pref.getString("email","No email")
            profile.gender = pref.getInt("gender",-1)
            profile.birthDate = pref.getString("birth_date","0")
            profile.api_token = pref.getString("api_token","0")
            profile.device_token = pref.getString("device_token","0")!!
            profile.mobile = pref.getString("mobile","0")
            profileId = pref.getString("profile_id","0")
        }
    }

    fun forceRefresh(){
        loadPreference()
    }
    companion object {
        private var instance: Preference? = null

        @JvmStatic
        fun getInstance(pref: SharedPreferences): Preference {
            if (instance == null) {
                instance = Preference(pref)
            }
            return instance!!
        }
    }
}