package com.autocrypt.covidmap.data.network

import com.autocrypt.covidmap.data.response.ResponseCovidVaccination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidVaccinationService {

    @GET("api/15077586/v1/centers")
    fun getCovidVaccinationCenter(
        @Query("perPage") perPage: Int,
        @Query("page") page: Int,
        @Query("serviceKey", encoded = false) serviceKey: String
    ): Call<ResponseCovidVaccination>
}