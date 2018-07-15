package com.orai.oraitest.presenter

import com.orai.oraitest.MyApp
import com.orai.oraitest.R
import com.orai.oraitest.helper.DateConverter
import com.orai.oraitest.interactor.network.NetworkInteractor
import com.orai.oraitest.models.SessionDetailedModel
import com.orai.oraitest.ui.media.player.OraiMediaPlayer
import com.orai.oraitest.ui.media.player.OraiMediaPlayerImpl
import com.orai.oraitest.ui.sessioninner.SessionInnerPresenter
import com.orai.oraitest.ui.sessioninner.SessionInnerView
import javax.inject.Inject

class SessionInnerPresenterImpl(private val view: SessionInnerView, myApp: MyApp) : SessionInnerPresenter {

    private val player: OraiMediaPlayer = OraiMediaPlayerImpl()

    init {
        myApp.appComponent.inject(this)
    }

    @Inject
    lateinit var networkInteractor: NetworkInteractor

    override fun onRenderView() {
        val sessionId = getSessionId()
        networkInteractor.getSessionById(sessionId) { session, error ->
            if (error != null || session == null) {
                error?.printStackTrace()
                view.showError()
            } else {
                updateSessionIntoView(session)
            }
        }
    }

    override fun onDestroy() {
        player.stop()
    }

    override fun onBackPressed() {
        view.startMainActivity()
    }

    override fun onPlayClicked() {
        val playButtonText = if (player.isPlaying()) {
            player.stop()
            R.string.play
        } else {
            player.start(getSessionId()){
                onPlayFinished()
            }
            R.string.stop
        }
        view.updatePlayButtonText(playButtonText)
    }

    private fun onPlayFinished(){
        view.updatePlayButtonText(R.string.start)
    }

    private fun updateSessionIntoView(session: SessionDetailedModel) {
        val dateStr = DateConverter.convertTimestampIntoData(session.timestamp)
        view.updateDate("Date = $dateStr")
        view.updateName("Session = ${session.sessionId}")
        view.updateNumFrames("Frame count = ${session.data.numFrames}")
        view.updateScore("Score = ${session.data.score}")
    }

    private fun getSessionId() = view.getSessionId()
}