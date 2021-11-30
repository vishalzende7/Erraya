package com.notocia.erraya.services.responses

import com.google.gson.annotations.SerializedName

class AllInterest {
    var status: Boolean = false
    var message: String = ""

    @SerializedName("data")
    var interest: ArrayList<Data>? = null

    class Data {
        var id: String = ""
        var status: Int = 0
        var created_at: String = ""
        var updated_at: String = ""
        var title: String = ""
    }
}