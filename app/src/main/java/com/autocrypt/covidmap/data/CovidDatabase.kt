package com.autocrypt.covidmap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.autocrypt.covidmap.data.dao.CovidDao
import com.autocrypt.covidmap.data.entity.CovidEntity

@Database(entities = [CovidEntity::class], version = 1, exportSchema = false)
abstract class CovidDatabase : RoomDatabase() {

    abstract fun getCovidDao(): CovidDao

    companion object {
        private var INSTANCE: CovidDatabase? = null

//        재사용을 하기 위해 싱글톤으로 구현했습니다.
        @Synchronized
        fun getInstance(context: Context): CovidDatabase? {
            if (INSTANCE == null) {
                synchronized(CovidDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CovidDatabase::class.java, "database"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}