package com.alexprojects.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.repository.SharedPreferences

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val sharedPreferences = SharedPreferences(application.applicationContext)

    fun logout() {
        sharedPreferences.remove(TaskConstants.SHARED.TOKEN_KEY)
        sharedPreferences.remove(TaskConstants.SHARED.PERSON_KEY)
        sharedPreferences.remove(TaskConstants.SHARED.PERSON_NAME)
    }
}