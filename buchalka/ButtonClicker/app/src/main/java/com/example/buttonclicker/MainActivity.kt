package com.example.buttonclicker

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var userInput: EditText
    private lateinit var button: Button
    private lateinit var textView: TextView
    private var numItemsClicked: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInput = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        textView.movementMethod = ScrollingMovementMethod()

        textView.text = "The button got tapped 0 times...\n"
        button.setOnClickListener {
            numItemsClicked += 1
            textView.append("The button got tapped $numItemsClicked ${if (numItemsClicked > 1) "times" else "time"}!\n")
        }
    }
}
