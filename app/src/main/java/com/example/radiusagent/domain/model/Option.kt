package com.example.radiusagent.domain.model

data class Option(
    val icon: String = "",
    val id: String = "",
    val name: String = "",
    var exclusion: HashMap<String, MutableList<String>> = hashMapOf()
)