package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.Constants.SAVED_OPERAND_1
import com.example.calculator.Constants.SAVED_OPERAND_1_STORED
import com.example.calculator.Constants.SAVED_PENDING_OPERATION

class MainActivity : AppCompatActivity() {
    private val result by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<EditText>(R.id.result)
    }
    private val newNumber by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<EditText>(R.id.newNumber)
    }
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<TextView>(R.id.operation)
    }

    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        val onDataInputButtonClicked: ((View) -> Unit) = { v ->
            val button = v as Button
            newNumber.append(button.text)
        }

        button0.setOnClickListener(onDataInputButtonClicked)
        button1.setOnClickListener(onDataInputButtonClicked)
        button2.setOnClickListener(onDataInputButtonClicked)
        button3.setOnClickListener(onDataInputButtonClicked)
        button4.setOnClickListener(onDataInputButtonClicked)
        button5.setOnClickListener(onDataInputButtonClicked)
        button6.setOnClickListener(onDataInputButtonClicked)
        button7.setOnClickListener(onDataInputButtonClicked)
        button8.setOnClickListener(onDataInputButtonClicked)
        button9.setOnClickListener(onDataInputButtonClicked)
        buttonDot.setOnClickListener(onDataInputButtonClicked)

        val onOperationButtonClicked: ((View) -> Unit) = { v ->
            val operation = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, operation)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = operation
            displayOperation.text = pendingOperation
        }

        buttonEquals.setOnClickListener(onOperationButtonClicked)
        buttonDivide.setOnClickListener(onOperationButtonClicked)
        buttonMultiply.setOnClickListener(onOperationButtonClicked)
        buttonMinus.setOnClickListener(onOperationButtonClicked)
        buttonPlus.setOnClickListener(onOperationButtonClicked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pendingOperation = savedInstanceState.getString(SAVED_PENDING_OPERATION, "")
        displayOperation.text = pendingOperation
        operand1 = if (savedInstanceState.getBoolean(SAVED_OPERAND_1_STORED)) {
            savedInstanceState.putBoolean(SAVED_OPERAND_1_STORED, false)
            savedInstanceState.getDouble(SAVED_OPERAND_1)
        } else {
            null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_PENDING_OPERATION, pendingOperation)
        operand1?.let {
            outState.putDouble(SAVED_OPERAND_1, it)
            outState.putBoolean(SAVED_OPERAND_1_STORED, true)
        }
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> {
                    operand1 = if (value == 0.0) {
                        Double.NaN
                    } else {
                        operand1!! / value
                    }
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }

        result.setText(operand1.toString())
        newNumber.setText("")
    }
}
