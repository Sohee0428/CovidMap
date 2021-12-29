package com.autocrypt.covidmap.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.autocrypt.covidmap.data.entity.CovidEntity

@Dao
interface CovidDao {

    @Query("SELECT * FROM CovidEntity")
    suspend fun getAll(): List<CovidEntity>

    @Insert
    suspend fun insertAll(covidDataList: List<CovidEntity>)

    @Query("DELETE FROM CovidEntity")
    suspend fun deleteAll()
}