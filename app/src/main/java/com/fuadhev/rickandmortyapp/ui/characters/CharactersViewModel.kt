package com.fuadhev.rickandmortyapp.ui.characters

import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.fuadhev.rickandmortyapp.common.utils.GenderType
import com.fuadhev.rickandmortyapp.common.utils.SpeciesType
import com.fuadhev.rickandmortyapp.common.utils.StatusType
import com.fuadhev.rickandmortyapp.domain.model.CharactersUiModel
import com.fuadhev.rickandmortyapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repo: Repository) : ViewModel() {


    private val _characterState =
        MutableStateFlow<PagingData<CharactersUiModel>>(PagingData.empty())
    val characterState get() = _characterState

    private val _filterState = MutableStateFlow(
        FilterState(
            name = "",
            gender = GenderType.ALL,
            status = StatusType.ALL,
            species = SpeciesType.ALL
        )
    )
    val filterState = _filterState.asStateFlow()

    init {
        getCharacters()
    }


    fun getCharacters(
        name: String = filterState.value.name,
        status: StatusType = filterState.value.status,
        gender: GenderType = filterState.value.gender
    ) {

        repo.getCharacters(name, status.statusName, gender.genderName)
            .cachedIn(viewModelScope).onEach {
            _characterState.value = it

        }.launchIn(viewModelScope)


    }

    fun updateName(value: String) {
        _filterState.update {
            it.copy(name = value)
        }
    }

    fun updateGender(value: GenderType) {
        _filterState.update {
            it.copy(gender = value)
        }
    }

    fun updateSpecies(value: SpeciesType) {
        _filterState.update {
            it.copy(species = value)
        }
    }

    fun updateStatus(value: StatusType) {
        _filterState.update {
            it.copy(status = value)
        }
    }


}