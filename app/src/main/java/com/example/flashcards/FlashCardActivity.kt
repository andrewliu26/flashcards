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

    private lateinit var operand1TextView: TextView
    private lateinit var operand2TextView: TextView
    private lateinit var operatorTextView: TextView
    private lateinit var answerEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        operand1TextView = findViewById(R.id.operand1TextView)
        operand2TextView = findViewById(R.id.operand2TextView)
        operatorTextView = findViewById(R.id.operatorTextView)
        answerEditText = findViewById(R.id.answerEditText)

        val generateButton = findViewById<Button>(R.id.generateButton)
        val submitButton = findViewById<Button>(R.id.submitButton)

        if (savedInstanceState != null) {
            val problemsArray = savedInstanceState.getIntArray("problemsArray")
            problems = ArrayList(problemsArray?.map { Problem.fromInt(it) } ?: listOf())
            score = savedInstanceState.getInt("score")
            currentProblemIndex = savedInstanceState.getInt("currentProblemIndex")
//            problems = savedInstanceState.getSerializable("problems") as ArrayList<Problem>

            if (problems.isNotEmpty()) {
                displayProblem()
            }

            generateButton.isEnabled = currentProblemIndex >= problems.size
            submitButton.isEnabled = currentProblemIndex < problems.size
        } else {
            val welcomeToast = Toast.makeText(this, "Welcome ${intent.getStringExtra("username")}", Toast.LENGTH_SHORT)
            welcomeToast.show()

            generateButton.isEnabled = true
            submitButton.isEnabled = false
        }

        generateButton.setOnClickListener {
            problems = Problem.generateProblems(10) as ArrayList<Problem>
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
        if (currentProblemIndex >= problems.size) {
            return
        }

        val currentProblem = problems[currentProblemIndex]
        operand1TextView.text = currentProblem.operand1.toString()
        operand2TextView.text = currentProblem.operand2.toString()
        operatorTextView.text = if (currentProblem.isAddition) "+" else "-"
        answerEditText.text.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val problemsArray = problems.map { it.toInt() }.toIntArray()
        outState.putIntArray("problemsArray", problemsArray)
        outState.putInt("score", score)
        outState.putInt("currentProblemIndex", currentProblemIndex)
//        outState.putSerializable("problems", problems)
    }

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
}