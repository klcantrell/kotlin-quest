package com.kalalaucantrell.kmpdemo.android

import android.util.Log
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
                is Idle, is Loading ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Loading...")
                    }
                is Error ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ah, something went wrong. Try again later.")
                    }
                is FetchingNewCharacter, is Loaded ->
                    HorizontalPager(
                        count = viewModel.characterCount,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        CharacterView(
                            state = state,
                            character = viewModel.getCharacter((page + 1).toString()),
                            onAppear = {
                                Log.i("MainLayout", "onAppear for ${(page + 1)}")
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
