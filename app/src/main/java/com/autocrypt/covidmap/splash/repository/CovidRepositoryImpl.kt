package com.autocrypt.covidmap.splash.repository

import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.data.response.CovidInquiryResponse
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import com.autocrypt.covidmap.splash.repository.local.CovidLocalDataSource
import com.autocrypt.covidmap.splash.repository.local.CovidLocalDataSourceImpl
import com.autocrypt.covidmap.splash.repository.remote.CovidRemoteDataSource
import com.autocrypt.covidmap.splash.repository.remote.CovidRemoteDataSourceImpl
import retrofit2.Callback

class CovidRepositoryImpl : CovidRepository {
    private val covidLocalDataSource: CovidLocalDataSource = CovidLocalDataSourceImpl()
    private val covidRemoteDataSource: CovidRemoteDataSource = CovidRemoteDataSourceImpl()

    override fun getCovidInquiryCenter(
        request: RequestCovidInquiry,
        callback: Callback<CovidInquiryResponse>
    ) {
        covidRemoteDataSource.getCovidInquiryCenter(request, callback)
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