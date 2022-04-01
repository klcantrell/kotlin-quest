package com.kalalaucantrell.kmpdemo.android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.CharacterLoaded
import com.kalalaucantrell.kmpdemo.android.SwApiUiState.FetchingNewCharacter
import com.kalalaucantrell.kmpdemo.network.Character

@Composable
fun CharacterView(state: SwApiUiState, character: Character?, onAppear: () -> Unit) {
    LaunchedEffect(onAppear) {
        onAppear()
    }

    Column {
        when (state) {
            is FetchingNewCharacter ->
                if (character?.id != null && state.characterId != character.id) {
                    CharacterCard(character)
                } else {
                    CircularProgressIndicator()
                }
            is CharacterLoaded ->
                if (character == null) {
                    Text(
                        "Something went wrong loading this character. Please try again later.",
                        modifier = Modifier
                            .padding(horizontal = 64.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    CharacterCard(character)
                }
            else -> Text(
                "Something went wrong loading the app. Please try again later.",
                modifier = Modifier
                    .padding(horizontal = 64.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://res.cloudinary.com/kalalau/kmpdemo/${character.id}.jpg")
                .crossfade(300)
                .build(),
            placeholder = painterResource(R.drawable.ic_round_square),
            error = painterResource(R.drawable.ic_empire_emblem),
            contentDescription = "Image for character ${character.id}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(300.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(Modifier.height(10.dp))
        Text(
            character.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))
        Text(
            "Appears in: ${character.appearsIn.joinToString(", ")}",
            modifier = Modifier
                .padding(horizontal = 32.dp),
        )
    }
}
