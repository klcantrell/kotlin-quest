package com.example.helloworld

fun main() {
    val telluride = Car("Telluride", "Kia")
    val fit = ElectricCar("Fit", "Honda", 30.0)

    telluride.drive(40.0)
    println(telluride.drive())
    telluride.extendRange(80.0)
    telluride.brake()

    fit.drive(50.0)
    fit.drive()
    fit.brake()
}

open class Car(val name: String, var brand: String) : Drivable {
    open var range: Double = 0.0
    override val maxSpeed: Double = 80.0
    fun extendRange(amount: Double) {
        if (amount > 0) {
            range += amount
        }
    }
    open fun drive(distance: Double) {
        println("Drove for $distance miles")
    }
    override fun drive(): String {
        brand = "morphed!"
        return "$name is driving!"
    }
    override fun brake() {
        println("Car of $brand brand is braking!")
    }
}

class ElectricCar(name: String, brand: String, val batteryLife: Double) : Car(name, brand) {
    override val maxSpeed: Double = 100.0
    override var range = batteryLife * 6
    override fun drive(distance: Double) {
        println("Drove for $distance with electricity")
    }
    override fun drive(): String {
        return "Drove until it couldn't drive anymore for $range miles"
    }
}

interface Drivable {
    val maxSpeed: Double
    fun drive(): String
    fun brake() {
        println("The drivable object is braking")
    }
}
