package com.notocia.erraya.utils

import com.notocia.erraya.models.Photo

class Profile {

    var mobile: String? = null

    var email: String? = null

    var profileType: ProfileType = ProfileType.NEW

    var mobile_verification_token: String? = null

    var device_token: String = "invi1155ssds54ds4ds4"

    var api_token: String? = null

    var name: String? = null
    var birthDate: String? = null
    var gender = 2
    var photos:ArrayList<Photo>? = null

    companion object {
        private var instance: Profile? = null
        fun getInstance(): Profile {
            if (instance == null) {
                instance = Profile()
            }
            return instance!!
        }
    }

    enum class ProfileType(i: Int) {
        EXIST(0),
        NEW(1)
    }

}