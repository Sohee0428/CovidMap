package com.autocrypt.covidmap.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.splash.repository.CovidRepository
import com.autocrypt.covidmap.splash.repository.CovidRepositoryImpl

class MapViewModel : ViewModel() {
    private val repository: CovidRepository = CovidRepositoryImpl()

    private val _covidDataList = MutableLiveData<List<CovidEntity>>()
    val covidDataList: LiveData<List<CovidEntity>>
        get() = _covidDataList

    suspend fun getAll() {
        _covidDataList.postValue(repository.getCovidDataList())
    }
}