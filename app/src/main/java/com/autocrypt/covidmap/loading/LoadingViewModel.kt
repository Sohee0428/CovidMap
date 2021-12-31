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

    private val _showFailMessage = MutableLiveData<Unit>()
    val showFailMessage: LiveData<Unit>
        get() = _showFailMessage

    suspend fun getCovidVaccinationCenter() {
        val request = RequestCovidVaccination(page, DEFAULT_PER_PAGE)
        val callback = object : Callback<ResponseCovidVaccination> {
            override fun onResponse(
                call: Call<ResponseCovidVaccination>,
                responseCovidVaccination: Response<ResponseCovidVaccination>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (responseCovidVaccination.isSuccessful.not()) {
                        _showFailMessage.postValue(Unit)
                        return@launch
                    }
                    insertDB(responseCovidVaccination.body())

                    if (page == 10) {
                        CoroutineScope(Main).launch {
                            _startMapActivityEvent.value = Unit
                        }
                    } else {
                        page++
                        getCovidVaccinationCenter()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseCovidVaccination>, t: Throwable) {
                _showFailMessage.postValue(Unit)
            }
        }
        repository.getCovidVaccinationCenter(request, callback)
    }

    private suspend fun insertDB(response: ResponseCovidVaccination?) {
        if (response == null) {
            _showFailMessage.postValue(Unit)
        } else {
            val entityList =
                ResponseCovidVaccination.toEntityList(response)
            repository.insertAll(entityList)
        }
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }

    companion object {
        const val DEFAULT_PER_PAGE = 10
    }
}