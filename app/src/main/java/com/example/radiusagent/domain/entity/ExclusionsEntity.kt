package com.example.radiusagent.domain.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

open class ExclusionsEntity(
) : RealmObject {

    var exclusions: RealmList<ExclusionEntity> = realmListOf()

    constructor(exclusions: RealmList<ExclusionEntity>) : this() {
        this.exclusions = exclusions
    }
}

open class ExclusionEntity(

) : RealmObject {
    var facilityId: String = ""
    var optionsId: String = ""

    constructor(facilityId: String, optionsId: String) : this() {
        this.facilityId = facilityId
        this.optionsId = optionsId
    }
}
