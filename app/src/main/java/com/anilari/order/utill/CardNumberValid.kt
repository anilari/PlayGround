package com.anilari.order.utill

fun valid(number: String): Boolean {
    var s1 = 0
    var s2 = 0
    var cardNumber = number.trim()
    val reverse = StringBuffer(cardNumber).reverse().toString()
    for (i in reverse.indices) {
        val digit = Character.digit(reverse[i], 10)
        when {
            i % 2 == 0 -> s1 += digit
            else -> {
                s2 += 2 * digit
                when {
                    digit >= 5 -> s2 -= 9
                }
            }
        }
    }
    return (s1 + s2) % 10 == 0
}