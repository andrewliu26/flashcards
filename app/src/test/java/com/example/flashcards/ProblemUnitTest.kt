package com.example.flashcards

import org.junit.Test
import org.junit.Assert.assertEquals

class ProblemUnitTest {

    @Test
    fun addition_isCorrect() {
        val problem = FlashCardActivity.Problem(true, 5, 3)
        assertEquals(8, problem.getAnswer())
    }

    @Test
    fun subtraction_isCorrect() {
        val problem = FlashCardActivity.Problem(false, 5, 3)
        assertEquals(2, problem.getAnswer())
    }
}
