package com.example.radiusagent.domain.usecases

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import com.example.radiusagent.domain.model.Facilities
import com.example.radiusagent.domain.model.Facility
import com.example.radiusagent.domain.repository.FacilitiesRepository
import com.example.radiusagent.domain.workmanager.WorkMangerHandler
import com.example.radiusagent.util.Resource
import com.example.radiusagent.util.workInfoResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAllFacilitiesUseCase @Inject constructor(
    private val facilitiesRepository: FacilitiesRepository,
    private val workMangerHandler: WorkMangerHandler,
    @ApplicationContext private val context: Context
) {

    private val workManager = WorkManager.getInstance(context)

    suspend operator fun invoke(): List<Facility>? {
        try {
            val state: Boolean

            val response = facilitiesRepository.checkDatabaseIsEmpty()
            if (response is Resource.Error) {
                return null
            }

            return if (response.data == true) {
                val workerRequest = workMangerHandler.startDatabaseWorker()
                state = workInfoResult(workManager, workerRequest!!.id)


                if (state) {
                    facilitiesRepository.getAllFacilities()?.apply {
                        setUpExclusionForEveryOption(this)
                    }?.facilities
                } else {
                    null
                }
            } else {
                facilitiesRepository.getAllFacilities()?.apply {
                    setUpExclusionForEveryOption(this)
                }?.facilities
            }

        } catch (exception: Exception) {
            Log.e(
                "GetAllFacilitiesUseCase",
                "An exception occurred inside getAllFacilities().Reason: $exception"
            )
            return null
        }
    }

    private fun setUpExclusionForEveryOption(facilities: Facilities) {
        facilities.facilities.forEach { facility ->
            facility.options.forEach {
                it.exclusion = getExclusion(
                    facilityId = facility.facilityId,
                    optionId = it.id,
                    facilities = facilities
                )
            }
        }
    }

    private fun getExclusion(
        facilityId: String,
        optionId: String,
        facilities: Facilities
    ): HashMap<String, MutableList<String>> {
        val map = hashMapOf<String, MutableList<String>>()

        facilities.facilities.forEach { facility: Facility ->
            if (facility.facilityId > facilityId) {
                map[facility.facilityId] = mutableListOf()
            }
        }

        facilities.exclusions.forEach {
            if (it[0].optionsId == optionId) {
                map[it[1].facilityId]?.add(it[1].optionsId)
            }
        }

        return map
    }

}