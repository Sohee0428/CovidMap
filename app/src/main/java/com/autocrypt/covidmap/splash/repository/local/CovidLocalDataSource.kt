package com.autocrypt.covidmap.splash.repository.local

import com.autocrypt.covidmap.data.entity.CovidEntity

interface CovidLocalDataSource {
    suspend fun getCovidDataList(): List<CovidEntity>
    suspend fun insertAll(covidData: List<CovidEntity>)
    suspend fun deleteAll()
}