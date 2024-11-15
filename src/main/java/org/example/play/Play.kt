package org.example.play

fun addToMap(map: LinkedHashMap<Int, Int>, key: Int, value: Int) {
    val valueInMap: Int? = map.get(key)
    if (valueInMap == null) {
        map[key] = value
    } else if (valueInMap == value) {
        println("ДУБЛИКАТ номер $key")
        map[key] = valueInMap.inc()
    }
}

fun main(args: Array<String>) {

    val min = listOf(
        333,
        272,
        342,
        331,
        296,
        346,
        311,
        330,
        335,
        350,
        264,
        266,
        298,
        281,
        292,
        265,
        285,
        276,
        273,
        286,
        341,
        319,
        348,
        345,
        287,
        320,
        289,
        301,
        268,
        343,
        309,
        332,
        336,
        277,
        270,
        308,
        275,
        267,
        305,
        340,
        278,
        307,
        269,
        299,
        338,
        306,
        334,
        265,
        324,
        288,
        325,
        329,
        303,
        324,
        271,
        290,
        279,
        302,
        283,
        274,
        328,
        304,
        323,
        295,
        337,
        315,
        314,
        344,
        310,
        300,
        276,
        297,
        312,
        293,
        322,
        321,
        339,
        313,
        349,
        284,
        282,
        326,
        318,
        316,
        291,
        280,
        327
    ).sorted().min()
    println(min)
//    val t = LinkedHashMap<Int, Int>()
//
//    while (true) {
//        val numberPayment = readln().toInt()
//        if (numberPayment == 0) {
//            break
//        }
//        addToMap(t, numberPayment, 1)
//        println(t.toString())
//    }
//    println("листы $t.toString(), количество листов ${t.size}")
}