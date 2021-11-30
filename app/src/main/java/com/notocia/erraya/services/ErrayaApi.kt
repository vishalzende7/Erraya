package com.notocia.erraya.services

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.notocia.erraya.models.Photo
import com.notocia.erraya.services.responses.*
import com.notocia.erraya.utils.Profile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ErrayaApi {

    private var client:Retrofit = Retrofit.Builder()
        .baseUrl("http://apierrya.bigint.in:5200/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun checkPhoneExist(mobile: String): Call<CheckPhone> {
        val apiService = client.create(ApiService::class.java)
        val jo = JsonObject()
        jo.addProperty("mobile", mobile)
        return apiService.checkPhoneExist(jo)
    }


    fun validateOtp(otp:String, dt:String, vt:String):Call<ValidateOtp>{
        val apiService = client.create(ApiService::class.java)
        val jo = JsonObject()
        jo.addProperty("device_tocken",dt)
        jo.addProperty("code",otp)
        jo.addProperty("verification_token",vt)
        return apiService.validateOtp(jo)
    }

    fun validateEmail(email:String):Call<EmailCheck>{
        val apiService = client.create(ApiService::class.java)
        val jo = JsonObject()
        jo.addProperty("email",email)
        return apiService.checkEmail(jo)
    }

    fun signup(profile: Profile):Call<Signup>{
        val apiService = client.create(ApiService::class.java)
        val jo = JsonObject()
        jo.addProperty("email",profile.email)
        jo.addProperty("device_tocken",profile.device_token)
        jo.addProperty("verification_token",profile.mobile_verification_token)
        jo.addProperty("name",profile.name)
        jo.addProperty("dbo",profile.birthDate)
        jo.addProperty("gender", profile.gender.toString())
        jo.addProperty("mobile",profile.mobile)
        return apiService.performSignUp(jo)
    }

    fun getProfile(token:String):Call<com.notocia.erraya.services.responses.Profile>{
        val apiService = client.create(ApiService::class.java)

        return apiService.getProfile("Bearer $token")
    }

    fun addPhoto(file:MultipartBody.Part, token: String):Call<UploadPhoto>{
        val apiService = client.create(ApiService::class.java)
        return apiService.uploadPhoto("Bearer $token",file)
    }

    fun updateOnSignup(bio:String, intrests:ArrayList<String>, images:List<Photo>, token: String):Call<UpdateSignup>{
        val apiService = client.create(ApiService::class.java)
        val jo = JsonObject()
        jo.addProperty("bio",bio)
        val intrestsArray = JsonArray()
        for(i:String in intrests){
            Log.i("MTTAG","Interest id $i")
            intrestsArray.add(i)
        }

        val imagesArray = JsonArray()
        for(i in images){
            if(i.server_id.isNotEmpty())
                imagesArray.add(i.server_id)
        }
        jo.add("intrests",intrestsArray)
        jo.add("images",imagesArray)
        return apiService.updateOnSignup(jo,"Bearer $token")
    }

    fun getAllInterest():Call<AllInterest>{
        val apiService = client.create(ApiService::class.java)
        return apiService.getAllInterest()
    }

    companion object{

        private var instance:ErrayaApi? = null

        @JvmStatic
        fun getInstance():ErrayaApi{
            if(instance == null){
                instance = ErrayaApi()
            }
            return instance!!
        }
    }

}