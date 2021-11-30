package com.notocia.erraya.services.responses

import com.google.gson.annotations.SerializedName

class Signup {
    @SerializedName("status")
    var status: Boolean = false

    @SerializedName("message")
    var msg: String = ""

    @SerializedName("token")
    var token: String = ""
}