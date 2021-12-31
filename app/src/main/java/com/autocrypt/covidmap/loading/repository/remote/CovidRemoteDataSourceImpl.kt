package com.autocrypt.covidmap.loading.repository.remote

import com.autocrypt.covidmap.App
import com.autocrypt.covidmap.R
import com.autocrypt.covidmap.data.network.RetrofitCreator
import com.autocrypt.covidmap.data.response.ResponseCovidVaccination
import com.autocrypt.covidmap.data.response.RequestCovidVaccination
import retrofit2.Callback

class CovidRemoteDataSourceImpl : CovidRemoteDataSource {
    private val covidInquiryService = RetrofitCreator.create()

    override fun getCovidVaccinationCenter(
        request: RequestCovidVaccination,
        callback: Callback<ResponseCovidVaccination>
    ) {
        covidInquiryService.getCovidVaccinationCenter(
            request.perPage,
            request.page,
            App.instance.getString(R.string.SERVICE_KEY)
        ).enqueue(callback)
    }
}