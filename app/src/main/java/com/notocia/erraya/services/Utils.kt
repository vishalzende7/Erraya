package com.notocia.erraya.services

import retrofit2.Call
import retrofit2.Callback

fun <T> Call<T>.onEnqueue(function: (Call<T>, retrofit2.Response<T>?, Throwable?) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
            function(call, response, null)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            function(call, null, t)
        }
    })

}