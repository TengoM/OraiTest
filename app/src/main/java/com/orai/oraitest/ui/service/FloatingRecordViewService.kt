package com.orai.oraitest.ui.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.orai.oraitest.R
import com.orai.oraitest.ui.media.OraiMedia
import com.orai.oraitest.ui.media.OraiMediaImpl
import com.orai.oraitest.ui.sessioninner.SessionInnerActivity


class FloatingRecordViewService : Service(), OraiMediaImpl.OraiMediaCallback {

    private var uFloatingView: View? = null

    private lateinit var oraiMedia: OraiMedia

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        setupView()
        initFloatingView()
        setupMedia()
    }

    override fun onDestroy() {
        super.onDestroy()
        oraiMedia.onDestroy()
    }

    private fun setupMedia() {
        oraiMedia = OraiMediaImpl(this)
    }

    private fun setupView() {
        initFloatingView()
        attachViewToWindow(uFloatingView)
    }

    private fun initFloatingView() {
        uFloatingView = LayoutInflater.from(this).inflate(R.layout.record_floating_view, null, false)
        uFloatingView?.findViewById<View>(R.id.uClose)?.setOnClickListener {
            stopAndRemoveView(it.parent as View)
        }
        setupMediaActionViews(uFloatingView!!)
    }

    private fun setupMediaActionViews(parent: View) {
        parent.findViewById<View>(R.id.uRecording).setOnClickListener {
            setupFloatingView(it.parent as View)
            val text = oraiMedia.onRecordClicked()
            (it as TextView).setText(text)
        }

        parent.findViewById<TextView>(R.id.uPlay)?.setOnClickListener {
            oraiMedia.onPlayTextClicked()
        }
        parent.findViewById<View>(R.id.uShowDetailed).setOnClickListener{
            startDetailedActivity(it.parent as View)
        }
    }

    private fun startDetailedActivity(floatingView: View){
        oraiMedia.provideSessionId()?.let {
            sessionId->
            val intent = SessionInnerActivity.getInstanceIntent(this, sessionId)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            stopAndRemoveView(floatingView)
        }
    }

    private fun stopAndRemoveView(view: View) {
        tryRemovingViewFromWindowManager(view)
        stopSelf()
    }

    override fun updateTime(timeInSecs: Int) {
        val timeView = uFloatingView?.findViewById<TextView>(R.id.uTime)
        timeView?.post {
            timeView.text = "sec= $timeInSecs"
        }
    }

    override fun onPlayFinished() = updatePlayButton(R.string.play)

    override fun onPlayStarted() = updatePlayButton(R.string.stop)

    private fun updatePlayButton(text: Int) {
        val uPlayText = uFloatingView?.findViewById<TextView>(R.id.uPlay)
        uPlayText?.setText(text)
    }

    private fun setupFloatingView(view: View) {
        uFloatingView = view
    }


    private fun attachViewToWindow(mFloatingView: View?) {
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.BOTTOM or Gravity.RIGHT
        params.x = 100
        params.y = 100

        getWindowManager().addView(mFloatingView, params)
    }

    private fun tryRemovingViewFromWindowManager(v: View) {
        try {
            getWindowManager().removeView(v)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getWindowManager() = getSystemService(Context.WINDOW_SERVICE) as WindowManager
}