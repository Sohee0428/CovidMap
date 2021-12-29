package com.autocrypt.covidmap.splash.repository

import com.autocrypt.covidmap.data.entity.CovidEntity
import com.autocrypt.covidmap.data.response.CovidInquiryResponse
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import retrofit2.Callback

interface CovidRepository {
    fun getCovidInquiryCenter(request: RequestCovidInquiry, callback: Callback<CovidInquiryResponse>)

    suspend fun getCovidDataList(): List<CovidEntity>
    suspend fun insertAll(covidData: List<CovidEntity>)
    suspend fun deleteAll()
}