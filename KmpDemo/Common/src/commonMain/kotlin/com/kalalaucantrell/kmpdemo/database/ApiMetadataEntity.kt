package com.kalalaucantrell.kmpdemo.database

import io.realm.RealmObject

class ApiMetadataEntity() : RealmObject {
    constructor(characterCount: Int) : this() {
        this.characterCount = characterCount
    }

    var characterCount: Int = 0
}