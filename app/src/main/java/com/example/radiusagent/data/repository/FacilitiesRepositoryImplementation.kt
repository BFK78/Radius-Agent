package com.example.radiusagent.data.repository

import android.util.Log
import com.example.radiusagent.data.remote.api.FacilitiesApi
import com.example.radiusagent.domain.entity.ExclusionsEntity
import com.example.radiusagent.domain.entity.FacilityEntity
import com.example.radiusagent.domain.entity.util.mapToExclusion
import com.example.radiusagent.domain.entity.util.mapToFacility
import com.example.radiusagent.domain.model.Facilities
import com.example.radiusagent.domain.repository.FacilitiesRepository
import com.example.radiusagent.util.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacilitiesRepositoryImplementation @Inject constructor(
    private val realm: Realm,
    private val facilitiesApi: FacilitiesApi
) : FacilitiesRepository {

    override suspend fun getAllFacilities(): Facilities? = withContext(Dispatchers.IO) {
        try {

            val facilityQuery = realm.query<FacilityEntity>()

            val exclusionQuery = realm.query<ExclusionsEntity>()

            val exclusionList = exclusionQuery.find().toList().map { exclusionsEntity ->
                exclusionsEntity.exclusions.toList().map {
                    it.mapToExclusion()
                }
            }

            val facilityList = facilityQuery.find().toList().map {
                it.mapToFacility()
            }

            Facilities(
                facilities = facilityList,
                exclusions = exclusionList
            )
        } catch (exception: Exception) {
            Log.e(
                "FacilitiesRepositoryImplementation",
                "An exception occurred inside getAllFacilities().Reason: $exception"
            )
            null
        }
    }

    override suspend fun fetchFacilitiesAndUpdateDatabase() {
        try {

            val facilitiesDTO = facilitiesApi.getAllFacilities()

            Log.i("basim", facilitiesDTO.toString())

            val facilitiesEntity = facilitiesDTO.facilities.map {
                it.mapToFacilityEntity()
            }

            facilitiesEntity.forEach {
                realm.write {
                    this.copyToRealm(it)
                }
            }

            facilitiesDTO.exclusions.map { dtos ->
                dtos.map {
                    it.mapToExclusionEntity()
                }
            }.forEach {
                realm.write {
                    copyToRealm(
                        ExclusionsEntity(
                            it.toRealmList()
                        )
                    )
                }
            }

        } catch (exception: Exception) {
            throw exception
        }
    }

    override suspend fun checkDatabaseIsEmpty(): Resource<Boolean> = withContext(Dispatchers.IO) {
        try {
            val facilityQuery = realm.query<FacilityEntity>()

            val facilities = facilityQuery.find().toList()

            Resource.Success(facilities.isEmpty())

        } catch (exception: Exception) {
            Log.e(
                "FacilitiesRepositoryImplementation",
                "An exception occurred inside checkDatabaseIsEmpty().Reason: $exception"
            )
            Resource.Error(false, message = exception.message.toString())
        }
    }
}