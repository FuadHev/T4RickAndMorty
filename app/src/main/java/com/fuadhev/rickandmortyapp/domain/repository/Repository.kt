package com.fuadhev.rickandmortyapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fuadhev.rickandmortyapp.data.network.api.CharacterApiService
import com.fuadhev.rickandmortyapp.paging_source.PagingDataSource
import javax.inject.Inject

class Repository @Inject constructor(val apiService: CharacterApiService) {

    fun getCharacters()= Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200
        ), pagingSourceFactory = { PagingDataSource(apiService) }
    ).flow


}