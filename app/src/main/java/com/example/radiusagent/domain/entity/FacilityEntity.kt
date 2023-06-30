package com.example.radiusagent.domain.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FacilityEntity() : RealmObject {

    @PrimaryKey
    var facilityId: String = ""
    var name: String = ""
    var options: RealmList<OptionEntity> = realmListOf()

    constructor(facilityId: String, name: String, options: RealmList<OptionEntity>) : this() {
        this.facilityId = facilityId
        this.name = name
        this.options = options
    }

}

open class OptionEntity() : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var icon: String = ""

    constructor(id: String, name: String, icon: String) : this() {
        this.id = id
        this.name = name
        this.icon = icon
    }
}



