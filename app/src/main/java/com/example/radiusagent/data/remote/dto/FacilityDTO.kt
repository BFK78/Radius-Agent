package com.example.radiusagent.data.remote.dto

import com.example.radiusagent.domain.entity.FacilityEntity
import io.realm.kotlin.ext.toRealmList

data class FacilityDTO(
    val facility_id: String,
    val name: String,
    val options: List<OptionDTO>
) {
    fun mapToFacilityEntity(): FacilityEntity = FacilityEntity(
        facilityId = facility_id,
        name = name,
        options = options.map {
            it.mapToOptionEntity()
        }.toRealmList()
    )
}