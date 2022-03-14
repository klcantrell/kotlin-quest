package com.kalalaucantrell.kmpdemo

import com.kalalaucantrell.kmpdemo.network.ApiCharacter
import com.kalalaucantrell.kmpdemo.network.SwApi

class SwApiRepository {
    private val api = SwApi()

    @Throws(Throwable::class)
    suspend fun getCharacterById(characterId: String): ApiCharacter {
        return api.getCharacterById(characterId)
    }
}
