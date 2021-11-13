package com.example.quizapp

object Constants {

    const val USER_NAME = "user_name"
    const val TOTAL_QUESTIONS = "total_question"
    const val CORRECT_ANSWERS = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val question1 = Question(
            id = 1,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_argentina,
            optionOne = "Argentina",
            optionTwo = "Austria",
            optionThree = "Armenia",
            optionFour = "Australia",
            correctAnswer = 1,
        )
        questionsList.add(question1)

        val question2 = Question(
            id = 2,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_australia,
            optionOne = "Angola",
            optionTwo = "Austria",
            optionThree = "Australia",
            optionFour = "Armenia",
            correctAnswer = 3,
        )
        questionsList.add(question2)

        val question3 = Question(
            id = 3,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_brazil,
            optionOne = "Belarus",
            optionTwo = "Belize",
            optionThree = "Brunei",
            optionFour = "Brazil",
            correctAnswer = 4,
        )
        questionsList.add(question3)

        val question4 = Question(
            id = 4,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_belgium,
            optionOne = "Bahamas",
            optionTwo = "Belgium",
            optionThree = "Barbados",
            optionFour = "Belize",
            correctAnswer = 2,
        )
        questionsList.add(question4)

        val question5 = Question(
            id = 5,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_fiji,
            optionOne = "Gabon",
            optionTwo = "France",
            optionThree = "Fiji",
            optionFour = "Finland",
            correctAnswer = 3,
        )
        questionsList.add(question5)

        val question6 = Question(
            id = 6,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_germany,
            optionOne = "Germany",
            optionTwo = "Georgia",
            optionThree = "Greece",
            optionFour = "none of these",
            correctAnswer = 1,
        )
        questionsList.add(question6)

        val question7 = Question(
            id = 7,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_denmark,
            optionOne = "Dominica",
            optionTwo = "Egypt",
            optionThree = "Denmark",
            optionFour = "Ethiopia",
            correctAnswer = 3,
        )
        questionsList.add(question7)

        val question8 = Question(
            8,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_india,
            optionOne = "Ireland",
            optionTwo = "Iran",
            optionThree = "Hungary",
            optionFour = "India",
            correctAnswer = 4,
        )
        questionsList.add(question8)

        val question9 = Question(
            9,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_new_zealand,
            optionOne = "Australia",
            optionTwo = "New Zealand",
            optionThree = "Tuvalu",
            optionFour = "United States of America",
            correctAnswer = 2,
        )
        questionsList.add(question9)

        val question10 = Question(
            10,
            questionText = "What country does this flag belong to?",
            image = R.drawable.ic_flag_of_kuwait,
            optionOne = "Kuwait",
            optionTwo = "Jordan",
            optionThree = "Sudan",
            optionFour = "Palestine",
            correctAnswer = 1,
        )
        questionsList.add(question10)

        return questionsList
    }
}
