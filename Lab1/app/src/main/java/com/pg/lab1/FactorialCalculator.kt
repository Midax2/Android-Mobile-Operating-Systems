package com.pg.lab1

import kotlinx.coroutines.delay

class FactorialCalculator {
    companion object {
        suspend fun calculateFactorial(number: Int): Long {
            if (number < 0) {
                throw IllegalArgumentException("Factorial is not defined for negative numbers.")
            }
            var result: Long = 1
            for (i in 1..number) {
                result *= i.toLong()
                delay(1000)
            }
            return result
        }
    }
}