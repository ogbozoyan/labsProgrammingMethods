package org.example.play

import kotlin.math.pow

/**
 * @author ogbozoyan
 * @since 31.07.2024
 */


fun main() {
    val P = 146000
    val r = 29.toDouble() / 12.toDouble() / 100.toDouble()
    val N = 5 * 12
    val D = 3
    val X = 48

    val PR = P * r
    val pow = (1 + r).pow(X)
    val PMT = PR / (1 - (1 + r).pow(-N))
    val P_X = (P * pow) - (PMT * (pow - 1) / r)
    val P2 = P_X * (1 + r).pow(D)
    val PMT2 =
        (P2 * r) /
                (1 - (1 + r).pow(-N - X - D))
    println(PMT)
    println(PMT2)
}