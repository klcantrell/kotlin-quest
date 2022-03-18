package com.kalalaucantrell.kmpdemo.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalalaucantrell.kmpdemo.SwApiService
import com.kalalaucantrell.kmpdemo.network.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SwApiViewModel : ViewModel() {
    val uiState: MutableStateFlow<SwApiUiState> = MutableStateFlow(SwApiUiState.Idle())

    val currentCharacter: Character?
        get() = swapiService.getCharacterById("1")

    private val swapiService = SwApiService()

    init {
        loadInitialData("1")
    }

    private fun loadInitialData(characterId: String) {
        viewModelScope.launch {
            uiState.value = SwApiUiState.Loading()
            swapiService.loadCharacterById(characterId)
            uiState.value = SwApiUiState.Loaded()
        }
    }
}

sealed class SwApiUiState {
    data class Idle(val idle: Boolean = true) : SwApiUiState()
    data class Loading(val loading: Boolean = true) : SwApiUiState()
    data class Loaded(val loaded: Boolean = true) : SwApiUiState()
}
