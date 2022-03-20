package com.kalalaucantrell.kmpdemo.android

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.*
import com.kalalaucantrell.kmpdemo.network.Character

@Composable
fun CharacterView(state: SwApiUiState, character: Character?, onAppear: () -> Unit) {
    LaunchedEffect(onAppear) {
        onAppear()
    }

    Column {
        when (state) {
            is Idle, is Loading -> Text("Loading next character...")
            is Error -> Text("Ah, something went wrong. Try again later.")
            is FetchingNewCharacter ->
                if (character?.id != null && state.characterId != character.id) {
                    Text(character.name)
                } else {
                    Text("Loading next character...")
                }
            is Loaded -> Text(
                character?.name ?: "Could not load this character, try again later.",
            )
        }
    }
}
