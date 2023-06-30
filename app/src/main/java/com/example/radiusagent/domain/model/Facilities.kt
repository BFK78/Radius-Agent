package com.example.radiusagent.domain.model

data class Facilities(
    val exclusions: List<List<Exclusion>>,
    val facilities: List<Facility>
)