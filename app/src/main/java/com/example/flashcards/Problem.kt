package com.example.flashcards

import kotlin.random.Random

internal data class Problem(val isAddition: Boolean, val operand1: Int, val operand2: Int) {
    fun getAnswer(): Int {
        return if (isAddition) operand1 + operand2 else operand1 - operand2
    }

    fun toInt() : Int {
        val op1 = String.format("%02d", operand1)
        val op2 = String.format("%02d", operand2)

        val operationInt = if (isAddition) 0 else 1

        return "$op1$op2$operationInt".toInt()
    }

    companion object {
        fun fromInt(intInput: Int): Problem {
            val strValue = intInput.toString()

            val operand1 = strValue.substring(0, 2).toInt()
            val operand2 = strValue.substring(2, 4).toInt()
            val isAddition = strValue.last() == '0'

            return Problem(isAddition, operand1, operand2)
        }
        fun generateProblems(count: Int ): List<Problem> {
            val problems = mutableListOf<Problem>()
            val half = count / 2

            for (i in 0 until half) {
                val operand1 = Random.nextInt(1, 100)
                val operand2 = Random.nextInt(1, 21)
                problems.add(Problem(true, operand1, operand2))
            }

            for (i in 0 until half) {
                val operand1 = Random.nextInt(1, 100)
                val operand2 = Random.nextInt(1, 21)
                problems.add(Problem(false, operand1, operand2))
            }

            problems.shuffle()
            return problems
        }
    }
}