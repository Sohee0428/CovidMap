package com.autocrypt.covidmap.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CovidEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lat: String,
    val lng: String,
    val centerType: String,
    val centerName: String,
    val address: String,
    val phoneNumber: String
) : Serializable
