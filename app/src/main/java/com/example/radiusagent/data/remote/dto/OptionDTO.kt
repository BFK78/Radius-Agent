package com.example.radiusagent.data.remote.dto

import com.example.radiusagent.domain.entity.OptionEntity

data class OptionDTO(
    val icon: String,
    val id: String,
    val name: String
) {
    fun mapToOptionEntity(): OptionEntity = OptionEntity(
        id = id,
        name = name,
        icon = icon
    )
}