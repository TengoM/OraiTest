package com.orai.oraitest.models

import com.google.gson.annotations.SerializedName

open class SessionModel {
    @SerializedName("sid")
    var sessionId: String = ""
    @SerializedName("processed")
    var processed: Boolean = false

    @SerializedName("timestamp")
    var timestamp: Long = -1
}