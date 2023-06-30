package com.example.radiusagent.data.remote.dto

data class FacilitiesDTO(
    val exclusions: List<List<ExclusionDTO>>,
    val facilities: List<FacilityDTO>
)
