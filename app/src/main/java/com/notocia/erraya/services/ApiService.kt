package com.notocia.erraya.services

import com.google.gson.JsonObject
import com.notocia.erraya.services.responses.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("api/user/phone/check")
    fun checkPhoneExist(@Body mobile: JsonObject): Call<CheckPhone>

    @PATCH("api/user/verify")
    fun validateOtp(@Body otp: JsonObject): Call<ValidateOtp>

    @POST("api/user/signup/email/check")
    fun checkEmail(@Body email: JsonObject): Call<EmailCheck>

    @POST("api/user/signup")
    fun performSignUp(@Body profile: JsonObject): Call<Signup>

    @GET("api/user/me")
    fun getProfile(@Header("Authorization") api_token: String): Call<Profile>

    @Multipart
    @POST("api/user/image/add")
    fun uploadPhoto(
        @Header("Authorization") api_token: String,
        @Part file: MultipartBody.Part,
    ): Call<UploadPhoto>

    @PATCH("api/user/signup/update/profile")
    fun updateOnSignup(
        @Body body: JsonObject,
        @Header("Authorization") api_token: String,
    ): Call<UpdateSignup>

    @GET("api/user/intrest-all")
    fun getAllInterest():Call<AllInterest>
}