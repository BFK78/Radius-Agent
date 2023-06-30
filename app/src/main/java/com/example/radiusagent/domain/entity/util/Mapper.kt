package com.example.radiusagent.domain.entity.util

import com.example.radiusagent.domain.entity.ExclusionEntity
import com.example.radiusagent.domain.entity.FacilityEntity
import com.example.radiusagent.domain.entity.OptionEntity
import com.example.radiusagent.domain.model.Exclusion
import com.example.radiusagent.domain.model.Facility
import com.example.radiusagent.domain.model.Option

fun FacilityEntity.mapToFacility(): Facility = Facility(
    facilityId = facilityId,
    name = name,
    options = options.map {
        it.mapToOption()
    }
)

fun OptionEntity.mapToOption(): Option = Option(
    icon = icon,
    id = id,
    name = name
)


fun ExclusionEntity.mapToExclusion(): Exclusion = Exclusion(
    facilityId, optionsId
)
