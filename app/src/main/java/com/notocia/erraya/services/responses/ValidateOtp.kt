package com.notocia.erraya.services.responses

import com.google.gson.annotations.SerializedName

class ValidateOtp {
    @SerializedName("status")
    var status: Boolean = false

    @SerializedName("message")
    var message: String = ""

    @SerializedName("token")
    var token: String = ""
}