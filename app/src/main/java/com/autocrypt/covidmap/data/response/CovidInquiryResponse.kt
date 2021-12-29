package com.autocrypt.covidmap.data.response

import com.autocrypt.covidmap.data.entity.CovidEntity

data class CovidInquiryResponse(
    val currentCount: Int,
    val data: List<Data>,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
) {
    companion object {
        fun toEntityList(covidInquiryResponse: CovidInquiryResponse) : List<CovidEntity> {
            return covidInquiryResponse.data.map {
                CovidEntity(
                    lat = it.lat,
                    lng = it.lng,
                    centerType = it.centerType,
                    centerName = it.centerName,
                    address = it.address,
                    phoneNumber = it.phoneNumber
                )
            }
        }
    }
}