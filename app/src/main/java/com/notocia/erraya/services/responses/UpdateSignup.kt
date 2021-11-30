package com.notocia.erraya.services.responses

import com.google.gson.annotations.SerializedName

class UpdateSignup {
    var status: Boolean = false
    var message: String = ""

    @SerializedName("data")
    var profileData: ProfileData? = null

    class ProfileData {
        var email: String = ""
        var id: String = ""

        @SerializedName("name")
        var name: String = ""
        var mobile: String = ""
        var is_phone_verified: Int = 1
        var is_admin: Int = 0
        var is_active: Int = 1
        var google_id: String = ""
        var facebook_id: String = ""
        var apple_id: String = ""
        var instagram_id: String = ""
        var verification_token: Int = 0
        var phone_verification_code: Int = 0
        var intrests: Array<String>? = null
        var profile_pic_url: String = ""
        var distance_filter: Int = 10
        var location: Long = 0
        var show_me: Int = 0
        var notification_get_match: Int = 0
        var notification_recive_message: Int = 0
        var notification_admin_update: Int = 0
        var notification_profile_not_update: Int = 0
        var notification_promotion: Int = 0
        var notification_updating_primium: Int = 0
        var notification_recive_like: Int = 0
        var notification_cross_limit_swip_like: Int = 0
        var notification_join_app_contact: Int = 0
        var work: String = ""
        var education: String = ""
        var city: String = ""
        var country: String = ""
        var city_id: String = ""
        var country_id: String = ""
        var rewind: Int = 0
        var super_like: Int = 0
        var swipe: Int = 0
        var boost: Int = 0
        var boost_time: Int = 0
        var plan: String = ""
        var is_disable: Int = 0
        var is_deleted: Int = 0
        var bio: String = ""
        var dbo: String = ""
        var gender: Int = 0
        var degination: String = ""
        var employee_id: String = ""
        var created_at: String = ""
        var updated_at: String = ""
        var pic_url: Array<String>? = null
        var verification_token_time: String = ""

        @SerializedName("_id")
        var profile_id: String = ""
    }
}