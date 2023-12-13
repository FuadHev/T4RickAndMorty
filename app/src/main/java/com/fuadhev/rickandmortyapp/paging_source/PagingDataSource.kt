package com.fuadhev.rickandmortyapp.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fuadhev.rickandmortyapp.data.network.api.CharacterApiService
import com.fuadhev.rickandmortyapp.data.network.dto.ErrorData
import com.fuadhev.rickandmortyapp.domain.mapper.Mapper.toCharactersUiModelList
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
import org.json.JSONObject


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

            val response = service.getCharacters(pageNumber, name, status, gender)
            if (response.isSuccessful) {
                val pagedResponse = response.body()
                val data = pagedResponse?.results?.toCharactersUiModelList()



                LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                    nextKey = pageNumber + 1
                )
            } else {

                if (response.code() == 404) {

                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )

                } else {
                    LoadResult.Error(Exception("Network call failed"))
                }
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun parseErrorData(errorBody: String?): ErrorData {

        val json = JSONObject(errorBody)
        val errorMessage = json.optString("message", "Unknown error")

        return ErrorData(errorMessage)
    }


}