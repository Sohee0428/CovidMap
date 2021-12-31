package com.autocrypt.covidmap.splash.repository.remote

import com.autocrypt.covidmap.App
import com.autocrypt.covidmap.R
import com.autocrypt.covidmap.data.network.RetrofitCreator
import com.autocrypt.covidmap.data.response.CovidVaccinationResponse
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import retrofit2.Callback

class CovidRemoteDataSourceImpl : CovidRemoteDataSource {
    private val covidInquiryService = RetrofitCreator.create()

    override fun getCovidVaccinationCenter(
        request: RequestCovidInquiry,
        callback: Callback<CovidVaccinationResponse>
    ) {
        covidInquiryService.getCovidVaccinationCenter(
            request.perPage,
            request.page,
            App.instance.getString(R.string.SERVICE_KEY)
        ).enqueue(callback)
    }
}