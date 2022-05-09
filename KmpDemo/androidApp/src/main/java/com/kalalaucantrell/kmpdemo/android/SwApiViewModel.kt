package com.kalalaucantrell.kmpdemo.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalalaucantrell.kmpdemo.SwApiService
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.*
import com.kalalaucantrell.kmpdemo.models.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SwApiViewModel : ViewModel() {
    val uiState: MutableStateFlow<SwApiUiState> = MutableStateFlow(Idle())

    val characterCount: Int
        get() = swapiService.getCharacterCount()

    fun getCharacter(characterId: String): Character? {
        return swapiService.getCharacterById(characterId)
    }

    private fun characterFailedToFetch(characterId: String): Boolean {
        return swapiService.characterFailedToFetch(characterId)
    }

    private val swapiService = SwApiService()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        uiState.value = Initializing()
        viewModelScope.launch {
            try {
                swapiService.loadInitialData()
                uiState.value = CharacterLoaded()
            } catch (error: Throwable) {
                uiState.value = InitializationError()
            }
        }
    }

    fun loadCharacter(characterId: String) {
        if (!uiState.value.isBusy) {
            if (getCharacter(characterId) != null) {
                return
            }
            if (characterFailedToFetch(characterId)) {
                return
            }
            uiState.value = FetchingNewCharacter(characterId)
            viewModelScope.launch {
                swapiService.loadCharacterById(characterId)
                uiState.value = CharacterLoaded()
            }
        }
    }
}

sealed class SwApiUiState {
    data class Idle(val idle: Boolean = true) : SwApiUiState()
    data class Initializing(val loading: Boolean = true) : SwApiUiState()
    data class FetchingNewCharacter(val characterId: String) : SwApiUiState()
    data class CharacterLoaded(val loaded: Boolean = true) : SwApiUiState()
    data class InitializationError(val error: Boolean = true) : SwApiUiState()

    val isBusy: Boolean
        get() = when (this) {
            is FetchingNewCharacter -> true
            else -> false
        }
}
