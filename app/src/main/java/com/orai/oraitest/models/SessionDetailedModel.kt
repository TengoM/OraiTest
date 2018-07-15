package com.orai.oraitest.models

import com.google.gson.annotations.SerializedName

class SessionDetailedModel: SessionModel() {

    @SerializedName("data")
    var data = Data()

    class Data{
        @SerializedName("score")
        var score = 0
        @SerializedName("length")
        var length = 0
        @SerializedName("num_frames")
        var numFrames = 0
    }
}