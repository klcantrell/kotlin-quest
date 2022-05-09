package com.kalalaucantrell.kmpdemo.database

import com.kalalaucantrell.kmpdemo.models.Character
import com.kalalaucantrell.kmpdemo.models.Movie
import io.realm.Realm
import io.realm.RealmConfiguration

class Realm {
    private val realm: Realm by lazy {
        val configuration = RealmConfiguration.with(
            schema = setOf(
                CharacterEntity::class,
                MovieEntity::class,
                ApiMetadataEntity::class,
            )
        )
        Realm.open(configuration)
    }

    suspend fun addCharacter(character: Character) {
        realm.write {
            copyToRealm(
                CharacterEntity(
                    id = character.id,
                    name = character.name,
                    appearsIn = character.appearsIn,
                )
            )
        }
    }

    fun getCharacterById(id: String): Character? {
        val results = realm.query(CharacterEntity::class, "id = $0", id).find()
        return if (results.count() == 0) {
            null
        } else {
            results.map {
                Character(
                    id = it.id,
                    name = it.name,
                    appearsIn = it.appearsIn
                )
            }[0]
        }
    }

    suspend fun addMovie(movie: Movie) {
        realm.write {
            copyToRealm(
                MovieEntity(
                    url = movie.url,
                    title = movie.title,
                )
            )
        }
    }

    fun getMovieByUrl(url: String): Movie? {
        val results = realm.query(MovieEntity::class, "url = $0", url).find()
        return if (results.count() == 0) {
            null
        } else {
            results.map {
                Movie(
                    url = it.url,
                    title = it.title,
                )
            }[0]
        }
    }

    suspend fun addCharacterCount(characterCount: Int) {
        realm.write {
            copyToRealm(
                ApiMetadataEntity(
                    characterCount = characterCount
                )
            )
        }
    }

    fun getCharacterCount(): Int {
        val results = realm.query(ApiMetadataEntity::class).find()
        return if (results.count() == 0) {
            return 0
        } else {
            results[0].characterCount
        }
    }
}
