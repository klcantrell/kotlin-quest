package com.kalalaucantrell.kmpdemo.android

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.*

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
                    IDLE, LOADING -> Text("Loading...")
                    ERROR -> Text("Ah, something went wrong. Try again later.")
                    FETCHING_NEW_CHARACTER, LOADED ->
                        Column {
                            Text(
                                viewModel.getCharacter()?.name
                                    ?: "Could not load this character, try again later.",
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Button(
                                onClick = {
                                    viewModel.currentCharacter += 1
                                    viewModel.loadCharacter(viewModel.currentCharacter.toString())
                                }
                            ) {
                                Text("Next character")
                            }
                        }
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
