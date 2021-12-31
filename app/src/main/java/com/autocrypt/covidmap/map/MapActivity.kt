package com.autocrypt.covidmap.map

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.autocrypt.covidmap.R
import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.databinding.ActivityMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private lateinit var naverMap: NaverMap
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        makeMap(savedInstanceState)
        initObserve()
    }

    override fun onMapReady(map: NaverMap) {
        CoroutineScope(Dispatchers.IO).launch {
            mapViewModel.getAll()
        }
        showMap(map)
    }

    private fun makeMap(savedInstanceState: Bundle?) {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(getString(R.string.NAVER_MAP_KEY))

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)
    }

    private fun showMap(map: NaverMap) {
        naverMap = map
        // 회사 위치를 기준으로 줌 설정
        val cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(37.5300106, 126.9262812), 12.0)
        naverMap.moveCamera(cameraUpdate)
    }

    private fun initObserve() {
        mapViewModel.covidDataList.observe(this) {
            it.forEach { item ->
                val marker = Marker().apply {
                    icon = OverlayImage.fromResource(checkCenterType(item.centerType))
                    position = LatLng(item.lat.toDouble(), item.lng.toDouble())
                    map = naverMap
                }
                initMarkerClickListener(marker, item)
            }
        }
    }

    private fun checkCenterType(centerType: String): Int {
        return when (centerType) {
            AREA -> R.drawable.ic_baseline_place_24
            REGION -> R.drawable.ic_baseline_place_25
            else -> R.drawable.ic_baseline_error_24
        }
    }

    private fun initMarkerClickListener(marker: Marker, item: CovidEntity) {
        marker.setOnClickListener {
            val addLocationDust = AlertDialog.Builder(this@MapActivity)
            addLocationDust.setTitle(R.string.vaccinationCenterInformation)
                .setMessage(
                    getString(R.string.vaccinationCenterName) + " : ${item.centerName}\n" +
                            getString(R.string.address) + " : ${item.address}\n" +
                            getString(R.string.phoneNumber) + " : ${item.phoneNumber}"
                )
                .setNegativeButton(R.string.close, null)
                .show()
            return@setOnClickListener true
        }
    }

    companion object {
        const val AREA = "중앙/권역"
        const val REGION = "지역"
    }
}