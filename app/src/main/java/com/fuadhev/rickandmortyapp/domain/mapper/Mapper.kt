package com.fuadhev.rickandmortyapp.domain.mapper

import com.fuadhev.rickandmortyapp.data.network.dto.Location
import com.fuadhev.rickandmortyapp.data.network.dto.Origin
import com.fuadhev.rickandmortyapp.data.network.dto.Result
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel

object Mapper {

    fun List<Result>.toCharactersUiModelList() = map {
        CharactersUiModel(
            it.created,
            it.episode,
            it.gender,
            it.id,
            it.image,
            it.location,
            it.name,
            it.origin,
            it.species,
            it.status,
            it.type,
            it.url
        )
    }

}