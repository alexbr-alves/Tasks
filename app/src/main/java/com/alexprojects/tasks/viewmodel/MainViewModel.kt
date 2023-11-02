package com.alexprojects.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.repository.SharedPreferences

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val sharedPreferences = SharedPreferences(application.applicationContext)

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun logout() {
        sharedPreferences.remove(TaskConstants.SHARED.TOKEN_KEY)
        sharedPreferences.remove(TaskConstants.SHARED.PERSON_KEY)
        sharedPreferences.remove(TaskConstants.SHARED.PERSON_NAME)
    }

    fun loadUserName() {
        _name.value = sharedPreferences.get(TaskConstants.SHARED.PERSON_NAME)
    }

}