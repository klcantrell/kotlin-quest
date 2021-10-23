package com.example.quizapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionsList = Constants.getQuestions()
        Log.i("Questions Size", questionsList.size.toString())

        val currentPosition = 1
        val question: Question = questionsList[currentPosition - 1]
        binding.progressBar.progress = currentPosition
        binding.tvProgress.text = "$currentPosition/${binding.progressBar.max}"
        binding.tvQuestion.text = question.questionText
        binding.ivImage.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour
    }
}
