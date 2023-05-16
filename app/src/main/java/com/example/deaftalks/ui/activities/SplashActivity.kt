package com.example.deaftalks.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.deaftalks.R

class SplashActivity : AppCompatActivity() {
    private val delayMillis: Long = 1000 // 1 second

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay the navigation to the second activity
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, LandingActivity::class.java)
//            startActivity(intent)
//            finish() // Optionally, finish the current activity
//        }, delayMillis)

    }
}