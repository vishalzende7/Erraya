package com.notocia.erraya.models

class Photo(uri: String?, server_id: String?, status: Boolean?) {
    var mUri: String = ""
    var server_id: String = ""
    var status: Boolean = false
    init {
        if(uri != null)
            mUri = uri
        if(server_id != null)
            this.server_id = server_id
        if(status != null)
            this.status = status
    }
}