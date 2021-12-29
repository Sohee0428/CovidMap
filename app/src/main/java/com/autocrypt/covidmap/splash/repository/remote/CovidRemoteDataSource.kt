package com.autocrypt.covidmap.splash.repository.remote

import com.autocrypt.covidmap.data.response.CovidInquiryResponse
import com.autocrypt.covidmap.data.response.RequestCovidInquiry
import retrofit2.Callback

interface CovidRemoteDataSource {
    fun getCovidInquiryCenter(request: RequestCovidInquiry, callback: Callback<CovidInquiryResponse>)
}