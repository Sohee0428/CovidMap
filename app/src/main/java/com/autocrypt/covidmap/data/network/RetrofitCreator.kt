package com.autocrypt.covidmap.data.network

import com.autocrypt.covidmap.App
import com.autocrypt.covidmap.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCreator {

    fun create(): CovidInquiryService {
        return Retrofit.Builder()
            .baseUrl(App.instance.getString(R.string.BASE_URL))
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CovidInquiryService::class.java)
    }

    private fun getClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}