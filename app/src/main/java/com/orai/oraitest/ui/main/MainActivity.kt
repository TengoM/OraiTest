package com.orai.oraitest.ui.main

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.View
import com.orai.oraitest.MyApp
import com.orai.oraitest.R
import com.orai.oraitest.base.mvp.BaseActivity
import com.orai.oraitest.extensions.havePermissionsFor
import com.orai.oraitest.extensions.makeToast
import com.orai.oraitest.models.SessionModel
import com.orai.oraitest.presenter.MainPresenterImpl
import com.orai.oraitest.ui.adapters.MainPageAdapter
import com.orai.oraitest.ui.service.FloatingRecordViewService
import com.orai.oraitest.ui.sessioninner.SessionInnerActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty.*


class MainActivity : BaseActivity(), MainView {

    companion object {
        const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 204

        fun getIntentInstance(context: Context) = Intent(context, MainActivity::class.java)

        val listOfPermissionsNeeded = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private lateinit var presenter: MainPresenter

    override val layoutId = R.layout.activity_main
    override fun renderView(savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        initAndStartPresenter()
        uFab.setOnClickListener {
            presenter.onNewSessionRequested()
        }
    }

    private fun initAndStartPresenter() {
        presenter = MainPresenterImpl(this, application as MyApp)
        presenter.onRenderView()
    }

    override fun showError(errorStringRes: Int) = makeToast(errorStringRes)

    override fun showEmptyLayout() {
        uEmptyLayout.visibility = View.GONE
    }

    override fun startSessionDetailActivityFor(sessionId: String) = startActivity(SessionInnerActivity.getInstanceIntent(this, sessionId))

    @TargetApi(Build.VERSION_CODES.M)
    override fun startFloatingSession() {
        if (havePermissionsFor(listOfPermissionsNeeded)) {
            initializeView()
        } else {
            ActivityCompat.requestPermissions(this, listOfPermissionsNeeded, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (havePermissionsFor(listOfPermissionsNeeded)) {
                initializeView()
            } else {
                makeToast("Draw over other app permission not available")
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initializeView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        } else {
            startService(Intent(this@MainActivity, FloatingRecordViewService::class.java))
            finish()
        }
    }

    override fun showFileList(sessionList: List<SessionModel>) {
        val adapter = MainPageAdapter(layoutInflater)
        uListView.adapter = adapter
        adapter.replaceItems(sessionList)
        uListView.setOnItemClickListener { _, _, position, _ ->
            val item = adapter.getItem(position)
            presenter.onSessionChosen(item)
        }
    }
}
