package com.orai.oraitest.interactor.network

import com.orai.oraitest.models.SessionDetailedModel
import com.orai.oraitest.models.SessionModel

interface NetworkInteractor {
    fun startGettingAllSessions(function: (sessions: List<SessionModel>?, error: Throwable?) -> Unit)
    fun getSessionById(sessionId: String, function: (session: SessionDetailedModel?, error: Throwable?) -> Unit)
}