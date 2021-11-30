package com.notocia.erraya.models

class DatingMode {
    private var id: Int = 0
    private var imgResource:Int = 0
    private var imgUrl:String = ""
    private var isGetFromUrl = false

    fun getId():Int{
        return id;
    }
    fun setId(id:Int){
        this.id = id
    }

    fun getImgResource():Int{
        return imgResource;
    }
    fun setImgResource(resourceId:Int){
        isGetFromUrl = false
        this.imgResource = resourceId
    }

    fun getImgUrl():String{
        return imgUrl;
    }
    fun setImgUrl(imgUrl:String){
        isGetFromUrl = true
        this.imgUrl = imgUrl
    }
}