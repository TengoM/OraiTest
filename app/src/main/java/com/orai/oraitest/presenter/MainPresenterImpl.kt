package com.orai.oraitest.presenter

import com.orai.oraitest.MyApp
import com.orai.oraitest.R
import com.orai.oraitest.interactor.network.NetworkInteractor
import com.orai.oraitest.models.SessionModel
import com.orai.oraitest.ui.main.MainPresenter
import com.orai.oraitest.ui.main.MainView
import javax.inject.Inject

class MainPresenterImpl(private val view: MainView, myApp: MyApp): MainPresenter {

    init {
        myApp.appComponent.inject(this)
    }

    @Inject
    lateinit var networkInteractor: NetworkInteractor

    override fun onRenderView() {
        networkInteractor.startGettingAllSessions{
            sessionList, error ->
            if(error == null && sessionList != null){
                onSuccessfullyGotData(sessionList)
            }else{
                showError(error)
            }
        }
    }

    override fun onSessionChosen(item: SessionModel) = view.startSessionDetailActivityFor(item.sessionId)

    override fun onNewSessionRequested() {
        view.startFloatingSession()
    }

    private fun showError(error: Throwable?) {
        error?.printStackTrace()
        view.showError(R.string.no_internet_connection)
    }

    private fun onSuccessfullyGotData(sessionList: List<SessionModel>) {
        val usableSession = sessionList.filter { it.processed }
        if(usableSession.isEmpty())
            view.showEmptyLayout()
        else
            view.showFileList(usableSession)
    }
}