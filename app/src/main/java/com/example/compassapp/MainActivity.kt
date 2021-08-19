package com.example.compassapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var compass: Compass? = null
    private var compassView: CompassView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compassView = findViewById(R.id.compassView)

        setupCompass()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(): Start compass.")
        compass?.start()
    }

    override fun onPause() {
        super.onPause()
        compass?.stop()
    }

    private fun setupCompass() {
        compass = Compass(this)
        val cl = getCompassListener()
        compass?.setListener(cl)
    }

    override fun onResume() {
        super.onResume()
        compass?.start()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(): Stop compass.")
        compass?.stop()
    }

    private fun getCompassListener(): Compass.CompassListener? {
        return object : Compass.CompassListener {
            override fun onNewAlpha(alpha: Float) {
                Thread(Runnable {
                    compassView?.adjustArrow(alpha)
                }).start()
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}