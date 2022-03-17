package com.kalalaucantrell.kmpdemo.network

import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class SwApi {
    private val httpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                val json =
                    Json { ignoreUnknownKeys = true; isLenient = true; useAlternativeNames = false }
                serializer = KotlinxSerializer(json)
            }
        }
    }

    suspend fun getAllCharacters(): ApiCharacterList {
        return httpClient.get("${Constants.BASE_API_URL}/people")
    }

    suspend fun getCharacterById(characterId: String): ApiCharacter {
        return httpClient.get("${Constants.BASE_API_URL}/people/${characterId}")
    }
}

object Constants {
    const val BASE_API_URL = "https://swapi.dev/api"
}
