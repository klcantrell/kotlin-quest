package com.kalalaucantrell.kmpdemo.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalalaucantrell.kmpdemo.SwApiService
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.*
import com.kalalaucantrell.kmpdemo.network.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SwApiViewModel : ViewModel() {
    val uiState: MutableStateFlow<SwApiUiState> = MutableStateFlow(IDLE)
    var currentCharacter = 1

    fun getCharacter(): Character? {
        return swapiService.getCharacterById(currentCharacter.toString())
    }

    private val swapiService = SwApiService()

    init {
        loadInitialData(currentCharacter.toString())
    }

    private fun loadInitialData(characterId: String) {
        viewModelScope.launch {
            uiState.value = LOADING
            swapiService.loadCharacterById(characterId)
            uiState.value = LOADED
        }
    }

    fun loadCharacter(characterId: String) {
        viewModelScope.launch {
            uiState.value = LOADING
            swapiService.loadCharacterById(characterId)
            uiState.value = LOADED
        }
    }
}

enum class SwApiUiState {
    IDLE,
    LOADING,
    FETCHING_NEW_CHARACTER,
    LOADED,
    ERROR;

    val isLoading: Boolean
        get() = when (this) {
            LOADING, FETCHING_NEW_CHARACTER -> true
            else -> false
        }
}
