package com.example.quizapp

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding

    private var currentQuestionAnswered = false
    private var currentQuestionPosition = 1
    private var questionsList = Constants.getQuestions()
    private var selectedOptionPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setQuestion()
        bindEventHandlers()
    }

    private fun setQuestion() {
        val currentQuestion = questionsList[currentQuestionPosition - 1]
        binding.progressBar.progress = currentQuestionPosition
        binding.tvProgress.text = "$currentQuestionPosition/${binding.progressBar.max}"
        binding.tvQuestion.text = currentQuestion.questionText
        binding.ivImage.setImageResource(currentQuestion.image)
        binding.tvOptionOne.text = currentQuestion.optionOne
        binding.tvOptionTwo.text = currentQuestion.optionTwo
        binding.tvOptionThree.text = currentQuestion.optionThree
        binding.tvOptionFour.text = currentQuestion.optionFour

        defaultOptionsView()

        if (currentQuestionPosition == questionsList.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
            binding.btnSubmit.text = "SUBMIT"
        }
    }

    private fun onClickOption(view: View) {
        when (view.id) {
            binding.tvOptionOne.id -> selectedOptionView(binding.tvOptionOne, 1)
            binding.tvOptionTwo.id -> selectedOptionView(binding.tvOptionTwo, 2)
            binding.tvOptionThree.id -> selectedOptionView(binding.tvOptionThree, 3)
            binding.tvOptionFour.id -> selectedOptionView(binding.tvOptionFour, 4)
            binding.btnSubmit.id -> {
                if (currentQuestionAnswered) {
                    currentQuestionPosition += 1

                    when {
                        currentQuestionPosition <= questionsList.size -> {
                            setQuestion()
                            currentQuestionAnswered = false
                        }
                        else -> {
                            currentQuestionAnswered = true
                            Toast.makeText(
                                this,
                                "You have successfully completed the quiz!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                } else {
                    currentQuestionAnswered = true
                    val question = questionsList[currentQuestionPosition - 1]
                    if (question.correctAnswer != selectedOptionPosition) {
                        if (selectedOptionPosition == 0) {
                            Toast.makeText(
                                this,
                                "You did not answer the question!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (currentQuestionPosition == questionsList.size) {
                        binding.btnSubmit.text = "FINISH"
                    } else {
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    selectedOptionPosition = 0
                }
            }
        }
    }

    private fun defaultOptionsView() {
        listOf(
            binding.tvOptionOne,
            binding.tvOptionTwo,
            binding.tvOptionThree,
            binding.tvOptionFour
        ).forEach {
            it.setTextColor(resources.getColor(R.color.gray_light_bluish))
            it.typeface = Typeface.DEFAULT
            it.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(view: TextView, selectOption: Int) {
        defaultOptionsView()
        selectedOptionPosition = selectOption
        view.setTextColor(resources.getColor(R.color.gray_dark_bluish))
        view.setTypeface(view.typeface, Typeface.BOLD)
        view.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private fun answerView(answer: Int, viewId: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background = ContextCompat.getDrawable(this, viewId)
            2 -> binding.tvOptionTwo.background = ContextCompat.getDrawable(this, viewId)
            3 -> binding.tvOptionThree.background = ContextCompat.getDrawable(this, viewId)
            4 -> binding.tvOptionFour.background = ContextCompat.getDrawable(this, viewId)
        }
    }

    private fun bindEventHandlers() {
        listOf(
            binding.tvOptionOne,
            binding.tvOptionTwo,
            binding.tvOptionThree,
            binding.tvOptionFour,
            binding.btnSubmit
        ).forEach { textView ->
            textView.setOnClickListener { onClickOption(textView) }
        }
    }
}
