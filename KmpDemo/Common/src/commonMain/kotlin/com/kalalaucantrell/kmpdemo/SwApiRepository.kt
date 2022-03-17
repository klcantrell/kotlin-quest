package com.kalalaucantrell.kmpdemo

import com.kalalaucantrell.kmpdemo.network.Character
import com.kalalaucantrell.kmpdemo.network.SwApi
import com.kalalaucantrell.kmpdemo.network.SwapiData

class SwApiRepository {
    private val api = SwApi()

    @Throws(Throwable::class)
    suspend fun getInitialData(): SwapiData {
        val count = api.getAllCharacters().count
        val initialCharacter = api.getCharacterById("1")
        return SwapiData(
            characterCount = count,
            characterData = mapOf("1" to Character("1", initialCharacter.name, emptyList()))
        )
    }

    @Throws(Throwable::class)
    suspend fun getCharacterById(characterId: String): Character {
        return Character(characterId, api.getCharacterById(characterId).name, emptyList())
    }
}
