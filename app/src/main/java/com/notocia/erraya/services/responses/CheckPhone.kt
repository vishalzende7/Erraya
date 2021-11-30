package com.notocia.erraya.services.responses

import com.google.gson.annotations.SerializedName

class CheckPhone {
    @SerializedName("status")
    var mStatus:Boolean = false

    @SerializedName("message")
    var msg:String = ""

    @SerializedName("status_code")
    var status_code:Int = -1 //Status code is set only when there is error else it will be -1

    @SerializedName("valData")
    var errors:Error? = null

    @SerializedName("tocken")
    var verification_token:String? = null
    class Error{
        @SerializedName("errors")
        var list:ArrayList<ErrorList> = ArrayList()
    }

    class ErrorList{
        var value:String  = ""
        var msg:String = ""
        var param:String = ""
        var location:String = ""
    }
}