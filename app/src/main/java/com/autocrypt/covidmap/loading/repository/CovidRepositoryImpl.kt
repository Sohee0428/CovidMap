package com.autocrypt.covidmap.loading.repository

import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.data.response.ResponseCovidVaccination
import com.autocrypt.covidmap.data.response.RequestCovidVaccination
import com.autocrypt.covidmap.loading.repository.local.CovidLocalDataSource
import com.autocrypt.covidmap.loading.repository.local.CovidLocalDataSourceImpl
import com.autocrypt.covidmap.loading.repository.remote.CovidRemoteDataSource
import com.autocrypt.covidmap.loading.repository.remote.CovidRemoteDataSourceImpl
import retrofit2.Callback

class CovidRepositoryImpl : CovidRepository {
    private val covidLocalDataSource: CovidLocalDataSource = CovidLocalDataSourceImpl()
    private val covidRemoteDataSource: CovidRemoteDataSource = CovidRemoteDataSourceImpl()

    override fun getCovidVaccinationCenter(
        request: RequestCovidVaccination,
        callback: Callback<ResponseCovidVaccination>
    ) {
        covidRemoteDataSource.getCovidVaccinationCenter(request, callback)
    }

    override suspend fun getCovidDataList(): List<CovidEntity> {
        return covidLocalDataSource.getCovidDataList()
    }

    override suspend fun insertAll(covidData: List<CovidEntity>) {
        covidLocalDataSource.insertAll(covidData)
    }

    override suspend fun deleteAll() {
        covidLocalDataSource.deleteAll()
    }


}