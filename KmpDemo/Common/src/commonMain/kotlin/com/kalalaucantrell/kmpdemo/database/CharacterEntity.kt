package com.kalalaucantrell.kmpdemo.database

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.realmListOf
import io.realm.toRealmList

class CharacterEntity() : RealmObject {
    constructor(id: String, name: String, appearsIn: List<String>) : this() {
        this.id = id
        this.name = name
        this.appearsIn = appearsIn.toRealmList()
    }

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var appearsIn: RealmList<String> = realmListOf()
}
