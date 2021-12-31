package com.autocrypt.covidmap.splash.repository.remote

import com.autocrypt.covidmap.data.response.CovidVaccinationResponse
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import retrofit2.Callback

interface CovidRemoteDataSource {
    fun getCovidVaccinationCenter(request: RequestCovidInquiry, callback: Callback<CovidVaccinationResponse>)
}