package com.example.flashcards

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class FlashCardActivity : AppCompatActivity() {

    private var problems = ArrayList<Problem>()
    private var score = 0
    private var currentProblemIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        val welcomeToast = Toast.makeText(this, "Welcome ${intent.getStringExtra("username")}", Toast.LENGTH_SHORT)
        welcomeToast.show()

        val generateButton = findViewById<Button>(R.id.generateButton)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val answerEditText = findViewById<EditText>(R.id.answerEditText)

        // Initially disable the submit button
        submitButton.isEnabled = false

        generateButton.setOnClickListener {
            problems = Problem.generateProblems() as ArrayList<Problem>
            currentProblemIndex = 0
            score = 0
            displayProblem()
            generateButton.isEnabled = false
            submitButton.isEnabled = true
        }

        submitButton.setOnClickListener {
            if (problems.isEmpty()) {
                Toast.makeText(this, "Please generate problems first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userAnswer = answerEditText.text.toString().toIntOrNull()

            if (userAnswer != null && userAnswer == problems[currentProblemIndex].getAnswer()) {
                score++
            }

            currentProblemIndex++

            if (currentProblemIndex >= problems.size) {
                Toast.makeText(this, "Score: $score out of 10", Toast.LENGTH_LONG).show()
                generateButton.isEnabled = true
                submitButton.isEnabled = false
            } else {
                displayProblem()
            }
        }
    }

    private fun displayProblem() {
        val currentProblem = problems[currentProblemIndex]

        val operand1TextView = findViewById<TextView>(R.id.operand1TextView)
        val operand2TextView = findViewById<TextView>(R.id.operand2TextView)
        val operatorTextView = findViewById<TextView>(R.id.operatorTextView)
        val answerEditText = findViewById<EditText>(R.id.answerEditText)

        operand1TextView.text = currentProblem.operand1.toString()
        operand2TextView.text = currentProblem.operand2.toString()
        operatorTextView.text = if (currentProblem.isAddition) "+" else "-"
        answerEditText.text.clear()
    }

    internal data class Problem(val isAddition: Boolean, val operand1: Int, val operand2: Int) {
        fun getAnswer(): Int {
            return if (isAddition) operand1 + operand2 else operand1 - operand2
        }

        companion object {
            fun generateProblems(): List<Problem> {
                val problems = mutableListOf<Problem>()

                val half = 5
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
}