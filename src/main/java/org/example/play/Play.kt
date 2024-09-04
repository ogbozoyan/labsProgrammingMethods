package org.example.play

/**
 * @author ogbozoyan
 * @since 27.07.2024
 */
class Play(play: Boolean) {
    constructor() : this(true)

}

fun foo(a: String = "a") = Unit
class Point(val x: Double, val y: Double) {
    constructor() : this(0.0, 0.0)

    companion object {
        fun fromPolar(angle: Double, radius: Double): Point {
            val x = radius * Math.cos(angle)
            val y = radius * Math.sin(angle)
            return Point(x, y)
        }
    }
}

fun main(args: Array<String>) {

    var play = Play()

    
    val list = listOf("a", "b", "c")

    println(list.stream().filter { it.length < 2 }.count())

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }

    val point = Point()
    val point2 = Point(1.3, 2.0)
    val point3 = Point(1.3, 2.0)
    println("${point.x}, ${point.y}")
    println(point2)
    println(point3)

}
