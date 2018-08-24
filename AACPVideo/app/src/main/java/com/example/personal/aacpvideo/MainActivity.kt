package com.example.personal.aacpvideo

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val VIDEO_SAMPLE = "tacoma_narrows"
    var mCurrentPosition = 0
    val CURRENT_POSITION = "current_position"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) mCurrentPosition = savedInstanceState.getInt(CURRENT_POSITION)
        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
    }

    private fun getMedia(mediaName: String): Uri =
            Uri.parse("android.resource://$packageName/raw/$mediaName")

    private fun initPlayer() {
        videoView.setVideoURI(getMedia(VIDEO_SAMPLE))
        if (mCurrentPosition > 0) videoView.seekTo(mCurrentPosition)
        else videoView.seekTo(1)
        videoView.start()
        videoView.setOnCompletionListener {
            Toast.makeText(this,"Se completo la reproduccion",Toast.LENGTH_SHORT).show()
            videoView.seekTo(1)
        }
    }

    private fun releasePlayer() = videoView.stopPlayback()

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) videoView.pause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(CURRENT_POSITION, videoView.currentPosition)
    }

}
