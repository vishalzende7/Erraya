package com.notocia.erraya.models

class MessageListItem() {

    var itemId:String = "na"
    var profileId:Long = 0
    var profileTitle:String = "Company Name"
    var profileSubTitle:String = "Lorem ipsum dolar sit amet"
    var imageResource:Int = 0
    constructor(itemId:String,profileId:Long, profileTitle:String, proSubT:String):this(){
        this.itemId = itemId
        this.profileId = profileId
        this.profileTitle = profileTitle
        this.profileSubTitle = proSubT
    }

    constructor(profileTitle:String, proSubT:String,image:Int):this(){
        this.profileTitle = profileTitle
        this.profileSubTitle = proSubT
        this.imageResource = image
    }
}