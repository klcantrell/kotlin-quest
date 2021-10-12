package com.example.helloworld

fun main() {
    myFunction()
}

fun myFunction() {
    val result = addUp(3, 5)
    println("result is $result")

    var name: String? = "Kal"
    name = null
    println("Length of name is ${name?.length}")
//    name?.let { println("Length of name is ${it.length}") }

    val myName = name ?: "default name"
    println(myName)
//    (name!! as String).lowercase()
    val me = Person("Kal", "Cantrell")
    val meAgain = Person("Kal")
    println(me.lastName)
    println(meAgain.lastName)

    val user1 = User(1, "Kal")
    val user2 = User(1, "Kal")
    println(user1.name)
    println(user1 == user2)
    println("User1 details: $user1")
    val user3 = user1.copy(name = "Kalalau")
    println(user1 == user3)
    println(user3.component1())
    println(user3.component2())
    val (user3Id, user3Name) = user3
    println("User 3 id: $user3Id")
    println("User 3 name: $user3Name")
}

fun addUp(a: Int, b: Int): Int {
    return a + b
}

class Person {
    var firstName: String
        get() {
            return field.lowercase()
        }
    var lastName: String
        get() {
            return field.uppercase()
        }
        private set

    constructor(firstName: String = "John", lastName: String = "Doe") {
        this.firstName = firstName
        this.lastName = lastName
    }

    constructor(firstName: String = "Johnny") {
        this.firstName = firstName
        this.lastName = "Default"
    }
}

data class User(val id: Long, val name: String)
