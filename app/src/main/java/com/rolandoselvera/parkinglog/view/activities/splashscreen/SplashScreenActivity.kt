package com.rolandoselvera.parkinglog.view.activities.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.rolandoselvera.parkinglog.R
import com.rolandoselvera.parkinglog.view.activities.main.MainActivity
import com.rolandoselvera.parkinglog.viewmodels.splashscreen.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        observeSplashLiveData()
    }

    private fun observeSplashLiveData() {

        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        splashViewModel.startDelaySplashScreen()

        splashViewModel.liveData.observe(this) {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }
    }
}