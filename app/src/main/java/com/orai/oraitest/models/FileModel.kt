package com.orai.oraitest.models

import com.google.gson.annotations.SerializedName

class FileModel: SessionModel() {

    @SerializedName("data")
    var dataList = emptyList<Data>()

    class Data{
        //{ "score": 0, "length": 25.073375, "num_frames": 401174 }

        @SerializedName("score")
        var score = 0

        @SerializedName("length")
        var lengthInSecs = 0.0

        @SerializedName("num_frames")
        var numFrames = 0

    }

}