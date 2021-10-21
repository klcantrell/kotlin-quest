package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.InputType.*
import com.example.mycalculator.databinding.ActivityMainBinding
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var inputType = NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindButtonHandlers()
    }

    private fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        inputType = when (inputType) {
            NONE, NEGATION_PENDING -> INTEGER
            INTEGER -> INTEGER
            FLOAT, DANGLING_DECIMAL -> FLOAT
            ONE_OPERAND_PENDING, TWO_OPERANDS_PENDING -> TWO_OPERANDS_PENDING
        }
    }

    private fun onClear(view: View) {
        binding.tvInput.text = ""
        inputType = NONE
    }

    private fun onDecimalPoint(view: View) {
        when (inputType) {
            NONE -> {
                binding.tvInput.append("0.")
                inputType = FLOAT
            }
            INTEGER -> {
                binding.tvInput.append(".")
                inputType = DANGLING_DECIMAL
            }
            DANGLING_DECIMAL,
            FLOAT,
            ONE_OPERAND_PENDING,
            TWO_OPERANDS_PENDING,
            NEGATION_PENDING -> Unit
        }
    }

    private fun onOperator(view: View) {
        inputType = when (inputType) {
            NONE -> {
                if ((view as Button).text == "-") {
                    binding.tvInput.text = "-"
                    INTEGER
                } else {
                    NONE
                }
            }
            NEGATION_PENDING -> {
                if ((view as Button).text == "-") {
                    binding.tvInput.text = ""
                    NONE
                } else {
                    NEGATION_PENDING
                }
            }
            DANGLING_DECIMAL -> {
                binding.tvInput.append("0${(view as Button).text}")
                FLOAT
            }
            INTEGER, FLOAT -> {
                binding.tvInput.append((view as Button).text)
                ONE_OPERAND_PENDING
            }
            ONE_OPERAND_PENDING -> ONE_OPERAND_PENDING
            TWO_OPERANDS_PENDING -> {
                val newText = performOperation(binding.tvInput.text.toString())
                binding.tvInput.text = newText.plus((view as Button).text)
                ONE_OPERAND_PENDING
            }
        }
    }

    private fun onEqual(view: View) {
        inputType = when (inputType) {
            NONE, DANGLING_DECIMAL, INTEGER, FLOAT, ONE_OPERAND_PENDING, NEGATION_PENDING -> inputType
            TWO_OPERANDS_PENDING -> {
                binding.tvInput.text = performOperation(binding.tvInput.text.toString())
                if (binding.tvInput.text.contains(".")) FLOAT else INTEGER
            }
        }
    }

    private fun bindButtonHandlers() {
        arrayOf(
            binding.buttonZero,
            binding.buttonOne,
            binding.buttonTwo,
            binding.buttonThree,
            binding.buttonFour,
            binding.buttonFive,
            binding.buttonSix,
            binding.buttonSeven,
            binding.buttonEight,
            binding.buttonNine,
        ).forEach { it.setOnClickListener(::onDigit) }

        arrayOf(
            binding.buttonDivide,
            binding.buttonMultiply,
            binding.buttonMinus,
            binding.buttonPlus,
        ).forEach { it.setOnClickListener(::onOperator) }

        binding.buttonClear.setOnClickListener(::onClear)
        binding.buttonDecimal.setOnClickListener(::onDecimalPoint)
        binding.buttonEqual.setOnClickListener(::onEqual)
    }

    private fun performOperation(expression: String): String {
        val absExpression = removeLeadingMinus(expression)
        val result: Float = when {
            absExpression.contains("+") -> {
                val arguments = getArguments(absExpression, '+')
                arguments[0] + arguments[1]
            }
            absExpression.contains("-") -> {
                val arguments = getArguments(absExpression, '-')
                arguments[0] - arguments[1]
            }
            absExpression.contains("*") -> {
                val arguments = getArguments(absExpression, '*')
                arguments[0] * arguments[1]
            }
            absExpression.contains("/") -> {
                val arguments = getArguments(absExpression, '/')
                arguments[0] / arguments[1]
            }
            else -> throw RuntimeException("Unsupported operation: $expression")
        }
        return if (floor(result.toDouble()) == result.toDouble()) result.toInt()
            .toString() else result.toString()
    }

    private fun getArguments(expression: String, delimiter: Char): List<Float> {
        val arguments = expression.split(delimiter).map { it.toFloat() }
        return listOf(
            if (expression.startsWith("-")) arguments[0].unaryMinus() else arguments[0],
            arguments[1]
        )
    }

    private fun removeLeadingMinus(expression: String): String {
        if (expression.startsWith("-")) {
            return expression.substring(1)
        }
        return expression
    }
}
