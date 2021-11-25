package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.Constants.SAVED_OPERAND_1
import com.example.calculator.Constants.SAVED_OPERAND_1_STORED
import com.example.calculator.Constants.SAVED_PENDING_OPERATION
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onDataInputButtonClicked: ((View) -> Unit) = { v ->
            val button = v as Button
            binding.newNumber.append(button.text)
        }

        binding.button0.setOnClickListener(onDataInputButtonClicked)
        binding.button1.setOnClickListener(onDataInputButtonClicked)
        binding.button2.setOnClickListener(onDataInputButtonClicked)
        binding.button3.setOnClickListener(onDataInputButtonClicked)
        binding.button4.setOnClickListener(onDataInputButtonClicked)
        binding.button5.setOnClickListener(onDataInputButtonClicked)
        binding.button6.setOnClickListener(onDataInputButtonClicked)
        binding.button7.setOnClickListener(onDataInputButtonClicked)
        binding.button8.setOnClickListener(onDataInputButtonClicked)
        binding.button9.setOnClickListener(onDataInputButtonClicked)
        binding.buttonDot.setOnClickListener(onDataInputButtonClicked)

        val onOperationButtonClicked: ((View) -> Unit) = { v ->
            val operation = (v as Button).text.toString()
            try {
                val value = binding.newNumber.text.toString().toDouble()
                performOperation(value, operation)
            } catch (e: NumberFormatException) {
                binding.newNumber.setText("")
            }
            pendingOperation = operation
            binding.operation.text = pendingOperation
        }

        binding.buttonEquals.setOnClickListener(onOperationButtonClicked)
        binding.buttonDivide.setOnClickListener(onOperationButtonClicked)
        binding.buttonMultiply.setOnClickListener(onOperationButtonClicked)
        binding.buttonMinus.setOnClickListener(onOperationButtonClicked)
        binding.buttonPlus.setOnClickListener(onOperationButtonClicked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pendingOperation = savedInstanceState.getString(SAVED_PENDING_OPERATION, "")
        binding.operation.text = pendingOperation
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

        binding.result.setText(operand1.toString())
        binding.newNumber.setText("")
    }
}
