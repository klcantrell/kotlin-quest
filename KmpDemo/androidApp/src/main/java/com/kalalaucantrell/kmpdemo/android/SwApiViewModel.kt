package com.kalalaucantrell.kmpdemo.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalalaucantrell.kmpdemo.SwApiService
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.*
import com.kalalaucantrell.kmpdemo.network.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SwApiViewModel : ViewModel() {
    val uiState: MutableStateFlow<SwApiUiState> = MutableStateFlow(Idle())

    val characterCount: Int
        get() = swapiService.getCharacterCount()

    fun getCharacter(characterId: String): Character? {
        return swapiService.getCharacterById(characterId)
    }

    private val swapiService = SwApiService()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        uiState.value = Loading()
        viewModelScope.launch {
            swapiService.loadInitialData()
            uiState.value = Loaded()
        }
    }

    fun loadCharacter(characterId: String) {
        if (getCharacter(characterId) != null) {
            return
        }
        if (!uiState.value.isLoading) {
            uiState.value = FetchingNewCharacter(characterId)
            viewModelScope.launch {
                swapiService.loadCharacterById(characterId)
                uiState.value = Loaded()
            }
        }
    }
}

sealed class SwApiUiState {
    data class Idle(val idle: Boolean = true) : SwApiUiState()
    class Loading(val loading: Boolean = true) : SwApiUiState()
    data class FetchingNewCharacter(val characterId: String) : SwApiUiState()
    data class Loaded(val loaded: Boolean = true) : SwApiUiState()
    data class Error(val error: Boolean = true) : SwApiUiState()

    val isLoading: Boolean
        get() = when (this) {
            is Loading, is FetchingNewCharacter -> true
            else -> false
        }
}
