package com.kalalaucantrell.kmpdemo.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainLayout(viewModel: SwApiViewModel) {
    val state: SwApiUiState by viewModel.uiState.collectAsState()

    MaterialTheme {
        Scaffold {
            when (state) {
                is Idle, is Initializing ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                is InitializationError ->
                    Text(
                        "Something went wrong loading the app. Please try again later.",
                        modifier = Modifier
                            .padding(horizontal = 64.dp),
                        textAlign = TextAlign.Center
                    )
                else ->
                    HorizontalPager(
                        count = viewModel.characterCount,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        CharacterView(
                            state = state,
                            character = viewModel.getCharacter((page + 1).toString()),
                            onAppear = {
                                viewModel.loadCharacter((page + 1).toString())
                            }
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
