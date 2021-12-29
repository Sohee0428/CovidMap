package com.autocrypt.covidmap.splash.repository.local

import com.autocrypt.covidmap.App
import com.autocrypt.covidmap.data.CovidDatabase
import com.autocrypt.covidmap.data.entity.CovidEntity

class CovidLocalDataSourceImpl: CovidLocalDataSource {
    private val localDB = CovidDatabase.getInstance(App.instance)
    private val covidDao = localDB!!.getCovidDao()

    override suspend fun getCovidDataList(): List<CovidEntity> {
        return covidDao.getAll()
    }

    override suspend fun insertAll(covidData: List<CovidEntity>) {
        covidDao.insertAll(covidData)
    }

    override suspend fun deleteAll() {
        covidDao.deleteAll()
    }
}