package com.autocrypt.covidmap.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autocrypt.covidmap.data.response.RequestCovidVaccination
import com.autocrypt.covidmap.data.response.ResponseCovidVaccination
import com.autocrypt.covidmap.loading.repository.CovidRepository
import com.autocrypt.covidmap.loading.repository.CovidRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadingViewModel : ViewModel() {
    private val repository: CovidRepository = CovidRepositoryImpl()
    private var page = 1

    private val _startMapActivityEvent = MutableLiveData<Unit>()
    val startMapActivityEvent: LiveData<Unit>
        get() = _startMapActivityEvent

    suspend fun getCovidInquiryCenter() {
        val request = RequestCovidInquiry(page, defaultPerPage)
        val callback = object : Callback<CovidVaccinationResponse> {
            override fun onResponse(
                call: Call<CovidVaccinationResponse>,
                response: Response<CovidVaccinationResponse>
            ) {
                val entityList = CovidVaccinationResponse.toEntityList(response.body()!!)

                CoroutineScope(Dispatchers.IO).launch {
                    insertDB(entityList)
                }
                if (page == 10) {
                    _startMapActivityEvent.value = Unit
                    CoroutineScope(Main).launch {
                    }
                } else {
                    page++
                    CoroutineScope(Dispatchers.IO).launch { getCovidInquiryCenter() }
                }
            }

            override fun onFailure(call: Call<CovidVaccinationResponse>, t: Throwable) {
            }
        }
        repository.getCovidVaccinationCenter(request, callback)
    }

    suspend fun insertDB(entityList: List<CovidEntity>) {
        repository.insertAll(entityList)
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }

    companion object {
        const val defaultPerPage = 10
    }
}