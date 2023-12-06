package com.fuadhev.rickandmortyapp.domain.model

import com.fuadhev.rickandmortyapp.data.network.dto.Location
import com.fuadhev.rickandmortyapp.data.network.dto.Origin
import java.io.Serializable

data class CharactersUiModel(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
):Serializable