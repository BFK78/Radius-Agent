package com.example.radiusagent.data.remote.api

import com.example.radiusagent.data.remote.dto.FacilitiesDTO
import retrofit2.http.GET

interface FacilitiesApi {
    @GET("/iranjith4/ad-assignment/db")
    suspend fun getAllFacilities(): FacilitiesDTO
}