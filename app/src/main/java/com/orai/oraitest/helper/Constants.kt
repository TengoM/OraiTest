package com.orai.oraitest.helper

object Constants{
    private const val LOCAL_HOST_WORK = "10.10.19.154"
    private const val LOCAL_HOST_HOME = "192.168.0.102"

    private const val LOCAL_HOST = LOCAL_HOST_HOME

    const val APP_BASE_URL = "http://$LOCAL_HOST:8000"
    const val SOCKET_URL = "ws://$LOCAL_HOST:8000/stream"

    fun getFilePathFromSession(curSessionId: String) = "$APP_BASE_URL/file/$curSessionId"

}