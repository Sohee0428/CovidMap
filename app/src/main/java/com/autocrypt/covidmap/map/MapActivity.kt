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

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("2q5r0wb55f")

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5300106, 126.9262812))
        naverMap.moveCamera(cameraUpdate)
        getMark(marker)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun getMark(marker: Marker) {
        marker.icon = OverlayImage.fromResource(R.drawable.ic_baseline_place_24)
//        marker.position = LatLng(37.5300106, 126.9262812)
        marker.position = LatLng(mapViewModel.getLatLng(), 126.9262812)

        mapViewModel.covidCenterLatLng.observe(this) {
            Log.d("mapLatLng", it.lat)
            marker.position = LatLng(it.lat.toDouble(), it.lng.toDouble())
//            marker.position = LatLng(mapViewModel.getLatLng()., it.lng.toDouble())
        }
        CoroutineScope(Dispatchers.Main).launch {
            mapViewModel.getLatLng()

//            marker.position = LatLng(mapViewModel.getLatLng, it.lng.toDouble())
        }
        marker.map = naverMap
    }
}