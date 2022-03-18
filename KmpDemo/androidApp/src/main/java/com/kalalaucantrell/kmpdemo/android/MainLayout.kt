package com.kalalaucantrell.kmpdemo.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainLayout(viewModel: SwApiViewModel) {
    val state: SwApiUiState by viewModel.uiState.collectAsState()

    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
                    is SwApiUiState.Idle, is SwApiUiState.Loading -> Text("Loading...")
                    is SwApiUiState.Loaded -> Text(
                        viewModel.currentCharacter?.name
                            ?: "Could not load this character, try again later."
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainActivityPreview() {
    MaterialTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Luke Skywalker")
            }
        }
    }
}
