package com.example.radiusagent.presentation.util

import com.example.radiusagent.R

fun String.mapToDrawable(): Int {
    return when (this) {
        "apartment" -> R.drawable.apartment
        "condo" -> R.drawable.condo
        "boat" -> R.drawable.boat
        "land" -> R.drawable.land
        "rooms" -> R.drawable.rooms
        "no-room" -> R.drawable.noroom
        "swimming" -> R.drawable.swimming
        "garden" -> R.drawable.garden
        "garage" -> R.drawable.garage
        else -> R.drawable.condo
    }
}