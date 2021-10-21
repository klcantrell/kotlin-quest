package com.example.mycalculator

enum class InputType {
    NONE,
    NEGATION_PENDING,
    INTEGER,
    DANGLING_DECIMAL,
    FLOAT,
    ONE_OPERAND_PENDING,
    TWO_OPERANDS_PENDING
}
