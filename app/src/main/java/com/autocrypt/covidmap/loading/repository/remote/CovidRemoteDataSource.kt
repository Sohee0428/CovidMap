package com.autocrypt.covidmap.loading.repository.remote

import com.autocrypt.covidmap.data.response.ResponseCovidVaccination
import com.autocrypt.covidmap.data.response.RequestCovidVaccination
import retrofit2.Callback

interface CovidRemoteDataSource {
    fun getCovidVaccinationCenter(request: RequestCovidVaccination, callback: Callback<ResponseCovidVaccination>)
}