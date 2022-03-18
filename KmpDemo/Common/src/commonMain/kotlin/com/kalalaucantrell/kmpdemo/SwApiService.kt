package com.kalalaucantrell.kmpdemo

import com.kalalaucantrell.kmpdemo.network.Character
import com.kalalaucantrell.kmpdemo.network.SwApi

class SwApiService {
    private val api = SwApi()

    private val characterData = mutableMapOf<String, Character>()
    private var count = 0

    @Throws(Throwable::class)
    suspend fun loadInitialData() {
        count = api.getAllCharacters().count
        val initialCharacter = api.getCharacterById("1")
        characterData["1"] = Character("1", initialCharacter.name, emptyList())
    }

    fun getCharacterCount(): Int {
        return count
    }

    fun getCharacterById(characterId: String): Character? {
        return characterData[characterId]
    }

    @Throws(Throwable::class)
    suspend fun loadCharacterById(characterId: String): Character {
        if (characterData[characterId] != null) {
            return characterData[characterId]!!
        }
        val apiCharacter = api.getCharacterById(characterId)
        val character = Character(
            characterId,
            apiCharacter.name,
            emptyList()
        )
        characterData[characterId] = character
        return character
    }
}
