package com.kalalaucantrell.kmpdemo

import com.kalalaucantrell.kmpdemo.database.Realm
import com.kalalaucantrell.kmpdemo.models.Character
import com.kalalaucantrell.kmpdemo.models.Movie
import com.kalalaucantrell.kmpdemo.network.SwApi

class SwApiService {
    private val api = SwApi()
    private val database = Realm()

    private val failedCharacters = mutableSetOf<String>()

    @Throws(Throwable::class)
    suspend fun loadInitialData() {
        if (initialDataCached()) {
            return
        }

        database.addCharacterCount(api.getAllCharacters().count)
        api.getAllMovies().results.forEach { movie ->
            database.addMovie(
                Movie(url = movie.url, title = movie.title)
            )
        }

        for (i in 1..5) {
            val character = api.getCharacterById(i.toString())
            val moviesAppearsIn = character.films
                .map { movieUrl ->
                    database.getMovieByUrl(movieUrl)?.title ?: "Unknown movie"
                }
            database.addCharacter(
                Character(
                    id = i.toString(),
                    name = character.name,
                    appearsIn = moviesAppearsIn
                )
            )
        }
    }

    fun getCharacterCount(): Int {
        return database.getCharacterCount()
    }

    fun getCharacterById(characterId: String): Character? {
        return database.getCharacterById(characterId)
    }

    fun characterFailedToFetch(characterId: String): Boolean {
        return failedCharacters.contains(characterId)
    }

    @Throws(Throwable::class)
    suspend fun loadCharacterById(characterId: String) {
        if (database.getCharacterById(characterId) != null) {
            return
        }
        try {
            val apiCharacter = api.getCharacterById(characterId)
            val moviesAppearsIn = apiCharacter.films
                .map { movieUrl -> database.getMovieByUrl(movieUrl)?.title ?: "Unknown movie" }
            val character = Character(
                characterId,
                apiCharacter.name,
                moviesAppearsIn
            )
            database.addCharacter(character)
            return
        } catch (error: Throwable) {
            failedCharacters.add(characterId)
        }
    }

    private fun initialDataCached(): Boolean {
        for (i in 1..5) {
            if (database.getCharacterById(i.toString()) == null) {
                return false
            }
        }
        return true
    }
}
