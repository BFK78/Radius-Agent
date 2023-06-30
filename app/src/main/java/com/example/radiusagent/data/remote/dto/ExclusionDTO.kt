package com.example.radiusagent.data.remote.dto

import com.example.radiusagent.domain.entity.ExclusionEntity

data class ExclusionDTO(
    val facility_id: String,
    val options_id: String
) {
    fun mapToExclusionEntity(): ExclusionEntity = ExclusionEntity(
        facilityId = facility_id,
        optionsId = options_id
    )
}