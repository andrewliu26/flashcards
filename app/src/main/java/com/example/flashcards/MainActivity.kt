package com.example.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeToast = Toast.makeText(this, "Welcome ${intent.getStringExtra("username")}", Toast.LENGTH_SHORT)
        welcomeToast.show()

    }
}