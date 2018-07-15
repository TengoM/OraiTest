package com.orai.oraitest.ui.sessioninner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.orai.oraitest.MyApp
import com.orai.oraitest.R
import com.orai.oraitest.base.mvp.BaseActivity
import com.orai.oraitest.presenter.SessionInnerPresenterImpl
import com.orai.oraitest.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_session_inner.*
import kotlinx.android.synthetic.main.empty.*

class SessionInnerActivity : BaseActivity(), SessionInnerView {

    companion object {
        const val SESSION_ID_EXTRA = "session_id"

        fun getInstanceIntent(context: Context, sessionId: String): Intent{
            val intent = Intent(context, SessionInnerActivity::class.java)
            intent.putExtra(SESSION_ID_EXTRA, sessionId)
            return intent
        }
    }

    private lateinit var presenter: SessionInnerPresenter

    override val layoutId = R.layout.activity_session_inner
    override fun renderView(savedInstanceState: Bundle?) {
        presenter = SessionInnerPresenterImpl(this, application as MyApp)
        presenter.onRenderView()
        setupClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }

    private fun setupClickListeners(){
        uPlay.setOnClickListener {
            presenter.onPlayClicked()
        }
    }

    override fun showError() {
        uEmptyLayout.visibility = View.VISIBLE
        uEmptyLayout.setText(R.string.something_went_wrong)
    }

    override fun updateDate(dateStr: String) {
        uDate.text = dateStr
    }

    override fun updateName(sessionId: String) {
        uSessionId.text = sessionId
    }

    override fun updateScore(score: String) {
        uScore.text = score
    }

    override fun updateNumFrames(numFrames: String) {
        uNumFrames.text = numFrames
    }

    override fun updatePlayButtonText(playButtonText: Int) = uPlay.setText(playButtonText)

    override fun startMainActivity() {
        finish()
        val instance = MainActivity.getIntentInstance(this)
        instance.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(instance)
    }

    override fun getSessionId() = intent.getStringExtra(SESSION_ID_EXTRA)?: ""
}
