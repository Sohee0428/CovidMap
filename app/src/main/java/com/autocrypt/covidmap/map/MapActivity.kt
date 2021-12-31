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
        makeMarker()
    }

    override fun onMapReady(map: NaverMap) {
        CoroutineScope(Dispatchers.IO).launch {
            mapViewModel.getAll()
        }
        showMap(map)
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

    private fun makeMap(savedInstanceState: Bundle?) {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("2q5r0wb55f")

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)
    }

    private fun showMap(map: NaverMap) {
        naverMap = map
        val cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(37.5300106, 126.9262812), 12.0)
        naverMap.moveCamera(cameraUpdate)
    }

    private fun makeMarker() {
        mapViewModel.covidDataList.observe(this) {
            it.forEach { item ->
                val marker = Marker()
                when (item.centerType) {
                    area -> marker.icon =
                        OverlayImage.fromResource(R.drawable.ic_baseline_place_24)
                    region -> marker.icon =
                        OverlayImage.fromResource(R.drawable.ic_baseline_place_25)
                }
                marker.position = LatLng(item.lat.toDouble(), item.lng.toDouble())
                marker.map = naverMap
                alertMarkerData(marker, item)
            }
        }
    }

    private fun alertMarkerData(marker: Marker, item: CovidEntity) {
        marker.setOnClickListener {
            val addLocationDust = AlertDialog.Builder(this@MapActivity)
            addLocationDust.setTitle(R.string.vaccinationCenterInformation)
                .setMessage(
                    "${R.string.vaccinationCenterName}" + " : ${item.centerName}\n" +
                            R.string.address + " : ${item.address}\n" +
                            R.string.phoneNumber + " : ${item.phoneNumber}"
                )
                .setNegativeButton(R.string.close, null)
                .show()
            return@setOnClickListener true
        }
    }

    companion object {
        const val area = "중앙/권역"
        const val region = "지역"
    }
}