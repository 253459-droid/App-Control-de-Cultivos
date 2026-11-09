package com.upchiapas.kt_template

import android.app.Application
import com.upchiapas.kt_template.jsonplaceholder.di.JsonPlaceholderModule
import com.upchiapas.kt_template.quiz.di.QuizModule

class KtApplication : Application() {
    val quizModule: QuizModule by lazy { QuizModule() }
    val jsonPlaceholderModule: JsonPlaceholderModule by lazy { JsonPlaceholderModule() }
}
