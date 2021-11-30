package com.notocia.erraya.models

class MatchItem(private val profileName:String,private val profileId:Int,private val profileImage:String) {

    fun profileName():String = profileName
    fun profileId():Int = profileId
    fun profileImage():String = profileImage

    override fun toString(): String {
        return "$profileId, $profileName, $profileImage"
    }
}