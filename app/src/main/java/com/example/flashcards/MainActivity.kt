package com.example.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var problems = ArrayList<Problem>()
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeToast = Toast.makeText(this, "Welcome ${intent.getStringExtra("username")}", Toast.LENGTH_SHORT)
        welcomeToast.show()

        if (savedInstanceState != null) {
            // Save Logic
        }

        val generateButton = findViewById<Button>(R.id.generateButton)
        generateButton.setOnClickListener {
            generateProblems()
            // Disable button after generating problems
            generateButton.isEnabled = false
        }

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            checkAnswers()
            generateButton.isEnabled = true
        }

    }

    private fun generateProblems() {
        problems.clear()
        for (i in 0 until 10) {
            val operand1 = Random.nextInt(1, 100)
            val operand2 = Random.nextInt(1, 21)
            problems.add(Problem(operand1, operand2))
        }
        displayProblems()
    }

    private fun displayProblems() {
        // Display Logic

    }

    private fun checkAnswers(){
        score = 0
        // Check Logic
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("problems", problems)
        outState.putInt("score", score)
    }
}