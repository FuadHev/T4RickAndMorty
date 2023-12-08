package com.fuadhev.rickandmortyapp.paging_source

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuadhev.rickandmortyapp.data.network.api.CharacterApiService
import com.fuadhev.rickandmortyapp.data.network.dto.Result
import com.fuadhev.rickandmortyapp.domain.mapper.Mapper.toCharactersUiModelList
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel


private const val STARTING_PAGE_INDEX = 1

class PagingDataSource(
    private val service: CharacterApiService,
    val name: String = "",
    val status: String = "",
    val gender: String = ""
) : PagingSource<Int, CharactersUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharactersUiModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersUiModel> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getCharacters(pageNumber,name,status,gender)
            val pagedResponse = response.body()
            val data = pagedResponse?.results?.toCharactersUiModelList()

//            var nextPageNumber: Int? = null

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}