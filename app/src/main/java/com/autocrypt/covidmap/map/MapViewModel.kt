package com.autocrypt.covidmap.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.splash.repository.CovidRepository
import com.autocrypt.covidmap.splash.repository.CovidRepositoryImpl
import com.autocrypt.covidmap.data.response.CovidInquiryResponse
import com.autocrypt.covidmap.data.response.Data
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel : ViewModel() {
    private val repository: CovidRepository = CovidRepositoryImpl()
    private var page = 1

    private val _startMapActivityEvent = MutableLiveData<Unit>()
    val startMapActivityEvent: LiveData<Unit>
        get() = _startMapActivityEvent

    private val _covidCenterLatLng = MutableLiveData<Data>()
    val covidCenterLatLng: LiveData<Data>
        get() = _covidCenterLatLng

    private val _covidCenterLatLng2 = MutableLiveData<CovidEntity>()
    val covidCenterLatLng2: LiveData<CovidEntity>
        get() = _covidCenterLatLng2

    fun getCovidInquiryCenter() {
        val request = RequestCovidInquiry(page, defaultPerPage)
        val callback = object : Callback<CovidInquiryResponse> {
            override fun onResponse(
                call: Call<CovidInquiryResponse>,
                response: Response<CovidInquiryResponse>
            ) {
                val entityList = CovidInquiryResponse.toEntityList(response.body()!!)

                CoroutineScope(Dispatchers.IO).launch {
                    insertDB(entityList)
                }

                if (request.page == 10) {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("jsh", "list >> " + repository.getCovidDataList().size)
                    }
                    _startMapActivityEvent.value = Unit
                    CoroutineScope(Main).launch {
                        _covidCenterLatLng.value = response.body()!!.data[1]
                    }
                } else {
                    page++
                    getCovidInquiryCenter()
                }
            }

            override fun onFailure(call: Call<CovidInquiryResponse>, t: Throwable) {
            }
        }
        repository.getCovidInquiryCenter(request, callback)
    }

    companion object {
        const val defaultPerPage = 10
    }

    suspend fun insertDB(entityList: List<CovidEntity>) {
        repository.insertAll(entityList)
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }

    suspend fun getLatLng() {
        val lat = repository.getCovidDataList()[1].lat
        val lng = repository.getCovidDataList()[1].lng
        Log.d("위경도", lat)
        CoroutineScope(Main).launch {
            _covidCenterLatLng2.value = repository.getCovidDataList()[1]
            Log.d("LatLng", repository.getCovidDataList()[1].lat)
        }
    }
}