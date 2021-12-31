package com.autocrypt.covidmap.loading.repository

import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.data.response.ResponseCovidVaccination
import com.autocrypt.covidmap.data.response.RequestCovidVaccination
import retrofit2.Callback

interface CovidRepository {
    fun getCovidVaccinationCenter(
        request: RequestCovidVaccination,
        callback: Callback<ResponseCovidVaccination>
    )

    suspend fun getCovidDataList(): List<CovidEntity>
    suspend fun insertAll(covidData: List<CovidEntity>)
    suspend fun deleteAll()
}