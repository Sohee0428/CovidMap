package com.autocrypt.covidmap.loading.repository.local

import com.autocrypt.covidmap.data.entity.CovidEntity

interface CovidLocalDataSource {
    suspend fun getCovidDataList(): List<CovidEntity>
    suspend fun insertAll(covidData: List<CovidEntity>)
    suspend fun deleteAll()
}