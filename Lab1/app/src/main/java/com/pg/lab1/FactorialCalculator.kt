package com.pg.lab1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FactorialCalculator {
    companion object {
        suspend fun calculateFactorial(number: Int, onProgressUpdate: (Int) -> Unit): Long {
            return withContext(Dispatchers.Default) {
                if (number < 0) {
                    throw IllegalArgumentException("Factorial is not defined for negative numbers.")
                }
                var result: Long = 1
                for (i in 1..number) {
                    result *= i.toLong()
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        onProgressUpdate(i * 100 / number)
                    }
                }
                result
            }
        }
    }
}