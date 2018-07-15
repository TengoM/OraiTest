package com.orai.oraitest.interactor.network.service

import com.orai.oraitest.models.SessionDetailedModel
import com.orai.oraitest.models.SessionModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkServices {
    companion object {
        private const val SESSION_ID_IDENTIFIER = "sesId"

        private const val SESSIONS = "sessions"
        private const val FILE = "file/{$SESSION_ID_IDENTIFIER}"
        private const val SESSION = "session/{$SESSION_ID_IDENTIFIER}"
    }

    @GET(SESSIONS)
    fun getAllSessions(): Call<List<SessionModel>>

    @GET(FILE)
    fun getFileWithId(@Path(value = SESSION_ID_IDENTIFIER, encoded = true) sessionId: String): Call<List<SessionModel>>

    @GET(SESSION)
    fun getSessionWithId(@Path(value = SESSION_ID_IDENTIFIER, encoded = true) sessionId: String): Call<SessionDetailedModel>

}