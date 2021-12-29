package com.autocrypt.covidmap.splash.repository.remote

import com.autocrypt.covidmap.App
import com.autocrypt.covidmap.R
import com.autocrypt.covidmap.data.network.RetrofitCreator
import com.autocrypt.covidmap.data.response.CovidInquiryResponse
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import retrofit2.Callback

class CovidRemoteDataSourceImpl : CovidRemoteDataSource {
    private val covidInquiryService = RetrofitCreator.create()

    override fun getCovidInquiryCenter(
        request: RequestCovidInquiry,
        callback: Callback<CovidInquiryResponse>
    ) {
        covidInquiryService.getCovidInquiryCenter(
            request.perPage,
            request.page,
            App.instance.getString(R.string.SERVICE_KEY)
        ).enqueue(callback)
    }
}