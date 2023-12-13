package com.fuadhev.rickandmortyapp.ui.characters

import com.fuadhev.rickandmortyapp.common.utils.GenderType
import com.fuadhev.rickandmortyapp.common.utils.SpeciesType
import com.fuadhev.rickandmortyapp.common.utils.StatusType

data class FilterState(
    val name: String,
    val gender: GenderType,
    val status: StatusType,
    val species: SpeciesType
)