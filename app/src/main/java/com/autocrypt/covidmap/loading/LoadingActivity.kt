package com.autocrypt.covidmap.loading

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.autocrypt.covidmap.R
import com.autocrypt.covidmap.databinding.ActivityLoadingBinding
import com.autocrypt.covidmap.map.MapActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {
    private val loadingViewModel: LoadingViewModel by viewModels()
    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)

        initObserve()
        initLocalData()
    }

    private fun initObserve() {
        loadingViewModel.startMapActivityEvent.observe(this) {
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }
        loadingViewModel.showFailMessage.observe(this) {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, getString(R.string.failMessage), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initLocalData() {
        CoroutineScope(Dispatchers.IO).launch {
            loadingViewModel.deleteAll()
            loadingViewModel.getCovidVaccinationCenter()
        }
    }
}