package com.example.radiusagent.domain.model

data class Facility(
    val facilityId: String = "",
    val name: String = "",
    var options: List<Option> = listOf()
)