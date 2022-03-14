package com.kalalaucantrell.kmpdemo.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalalaucantrell.kmpdemo.SwApiRepository
import com.kalalaucantrell.kmpdemo.network.ApiCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SwApiViewModel : ViewModel() {
    val uiState: MutableStateFlow<SwApiUiState> = MutableStateFlow(SwApiUiState.Idle())

    private val repository = SwApiRepository()

    init {
        getCharacterById("1")
    }

    private fun getCharacterById(characterId: String) {
        viewModelScope.launch {
            uiState.value = SwApiUiState.Loading()
            uiState.value = SwApiUiState.Success(repository.getCharacterById(characterId))
        }
    }
}

sealed class SwApiUiState {
    data class Idle(val idle: Boolean = true) : SwApiUiState()
    data class Loading(val loading: Boolean = true) : SwApiUiState()
    data class Success(val character: ApiCharacter) : SwApiUiState()
}
