package com.autocrypt.covidmap.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.autocrypt.covidmap.map.MapActivity
import com.autocrypt.covidmap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            splashViewModel.deleteAll()
        }
        splashViewModel.getCovidInquiryCenter()
        splashViewModel.startMapActivityEvent.observe(this) {
            startActivity( Intent(this, MapActivity::class.java))
            finish()
        }
    }
}