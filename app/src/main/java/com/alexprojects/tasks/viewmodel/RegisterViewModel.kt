package com.alexprojects.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PersonModel
import com.alexprojects.tasks.service.model.ValidationModel
import com.alexprojects.tasks.service.repository.PersonRepository
import com.alexprojects.tasks.service.repository.SharedPreferences
import com.alexprojects.tasks.service.repository.remote.RetrofitClient

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonRepository(application.applicationContext)
    private val sharedPreferences = SharedPreferences(application.applicationContext)

    private val _user = MutableLiveData<ValidationModel>()
    val user: LiveData<ValidationModel> = _user

    fun create(name: String, email: String, password: String) {
        repository.create(name, email, password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                sharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY, result.token)
                sharedPreferences.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)
                sharedPreferences.store(TaskConstants.SHARED.PERSON_NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)
                _user.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _user.value = ValidationModel(message)
            }

        })
    }

}