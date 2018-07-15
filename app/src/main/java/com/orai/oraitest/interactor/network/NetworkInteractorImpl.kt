package com.orai.oraitest.interactor.network

import com.orai.oraitest.interactor.network.service.NetworkServices
import com.orai.oraitest.interactor.network.throwables.ServerNoDataError
import com.orai.oraitest.interactor.network.throwables.ServerResponseUnknownThrowable
import com.orai.oraitest.models.SessionDetailedModel
import com.orai.oraitest.models.SessionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NetworkInteractorImpl(private val retrofit: Retrofit): NetworkInteractor {

    override fun startGettingAllSessions(function: (sessions: List<SessionModel>?, error: Throwable?) -> Unit) {
        val request = retrofit.create(NetworkServices::class.java).getAllSessions()
        sendRequestAndGetSuccessOrError(request, function)
    }

    override fun getSessionById(sessionId: String, function: (session: SessionDetailedModel?, error: Throwable?) -> Unit) {
        val request = retrofit.create(NetworkServices::class.java).getSessionWithId(sessionId)
        sendRequestAndGetSuccessOrError(request, function)
    }

    private fun <T> sendRequestAndGetSuccessOrError(request: Call<T>, action: (result: T?, error: Throwable?) -> Unit) {
        request.enqueue(object : Callback<T> {
            override fun onResponse(p0: Call<T>?, p1: Response<T>?) =
                    if (p1?.body() == null) {
                        action(null, ServerNoDataError())
                    } else {
                        action(p1.body(), null)
                    }

            override fun onFailure(p0: Call<T>?, p1: Throwable?) {
                if (p1 != null) {
                    action(null, p1)
                } else {
                    action(null, ServerResponseUnknownThrowable())
                }
            }
        })
    }

}