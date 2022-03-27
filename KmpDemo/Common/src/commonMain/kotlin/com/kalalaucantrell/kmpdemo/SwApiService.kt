package com.kalalaucantrell.kmpdemo

import com.kalalaucantrell.kmpdemo.network.Character
import com.kalalaucantrell.kmpdemo.network.SwApi

class SwApiService {
    private val api = SwApi()

    private val characterData = mutableMapOf<String, Character>()
    private val failedCharacters = mutableSetOf<String>()
    private var count = 0

    @Throws(Throwable::class)
    suspend fun loadInitialData() {
        count = api.getAllCharacters().count
        for (i in 1..5) {
            val character = api.getCharacterById(i.toString())
            characterData[i.toString()] = Character(i.toString(), character.name, emptyList())
        }
    }

    fun getCharacterCount(): Int {
        return count
    }

    fun getCharacterById(characterId: String): Character? {
        return characterData[characterId]
    }

    fun characterFailedToFetch(characterId: String): Boolean {
        return failedCharacters.contains(characterId)
    }

    @Throws(Throwable::class)
    suspend fun loadCharacterById(characterId: String) {
        if (characterData[characterId] != null) {
            return
        }
        try {
            val apiCharacter = api.getCharacterById(characterId)
            val character = Character(
                characterId,
                apiCharacter.name,
                emptyList()
            )
            characterData[characterId] = character
            return
        } catch (error: Throwable) {
            failedCharacters.add(characterId)
        }
    }
}
