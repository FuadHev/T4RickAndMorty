package com.fuadhev.rickandmortyapp.common.utils

enum class GenderType(val genderName: String) {
    ALL(""),
    MALE("male"),
    FEMALE("female"),
    GENDERLESS("genderless"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String) =
            values().find { it.genderName.lowercase() == value.lowercase() } ?: ALL
    }
}

enum class StatusType(val statusName: String) {
    ALL(""),
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String) =
            values().find { it.statusName.lowercase() == value.lowercase() } ?: ALL
    }
}

enum class SpeciesType(val speciesName: String) {
    ALL(""),
    HUMAN("human"),
    ALIEN("alien"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String) =
            values().find { it.speciesName.lowercase() == value.lowercase() } ?: ALL
    }
}