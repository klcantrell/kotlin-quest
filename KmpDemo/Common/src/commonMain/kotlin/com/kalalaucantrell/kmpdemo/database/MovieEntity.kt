package com.kalalaucantrell.kmpdemo.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class MovieEntity() : RealmObject {
    constructor(url: String, title: String) : this() {
        this.url = url
        this.title = title
    }

    @PrimaryKey
    var url: String = ""
    var title: String = ""
}
