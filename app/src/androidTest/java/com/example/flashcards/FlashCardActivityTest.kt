package com.example.flashcards

import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.isEmptyOrNullString
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.not

@RunWith(AndroidJUnit4::class)
class FlashCardActivityTest {

    private lateinit var scenario: ActivityScenario<FlashCardActivity>

    @Before
    fun setUp() {
        scenario = launch(FlashCardActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }


    @Test
    fun displaysGeneratedProblemOnGenerateButtonPress() {
        onView(withId(R.id.generateButton)).perform(click())
        onView(withId(R.id.operand1TextView)).check(matches(withText(not(isEmptyOrNullString()))))
        onView(withId(R.id.operand2TextView)).check(matches(withText(not(isEmptyOrNullString()))))
    }

    @Test
    fun doesNotCrashOnSubmit() {
        onView(withId(R.id.answerEditText)).perform(typeText("44"))
        onView(withId(R.id.submitButton)).perform(click())
        onView(withId(R.id.generateButton)).check(matches(isDisplayed()))
    }

}

