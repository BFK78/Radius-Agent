package com.example.radiusagent.domain.repository

import com.example.radiusagent.domain.model.Facilities
import com.example.radiusagent.util.Resource

interface FacilitiesRepository {
    suspend fun getAllFacilities(): Facilities?

    suspend fun fetchFacilitiesAndUpdateDatabase()

    suspend fun checkDatabaseIsEmpty(): Resource<Boolean>
}