package com.notocia.erraya.models

class BasicInfo(d:Int, value:String, catId:Int) {

    var drawable:Int = 0
    var value:String = "Basic Info"
    var catId:Int = 0

    init {
        drawable = d
        this.value = value
        this.catId = catId
    }
}