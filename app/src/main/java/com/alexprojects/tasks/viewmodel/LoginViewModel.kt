package com.alexprojects.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexprojects.tasks.helper.BiometricHelper
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PersonModel
import com.alexprojects.tasks.service.model.PriorityModel
import com.alexprojects.tasks.service.model.ValidationModel
import com.alexprojects.tasks.service.repository.PersonRepository
import com.alexprojects.tasks.service.repository.PriorityRepository
import com.alexprojects.tasks.service.repository.SharedPreferences
import com.alexprojects.tasks.service.repository.remote.RetrofitClient

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application.applicationContext)
    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val sharedPreferences = SharedPreferences(application.applicationContext)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    fun doLogin(email: String, password: String) {
        personRepository.login(email, password, object : APIListener<PersonModel> {

            override fun onSuccess(result: PersonModel) {
                sharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY, result.token)
                sharedPreferences.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)
                sharedPreferences.store(TaskConstants.SHARED.PERSON_NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)
                _login.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _login.value = ValidationModel(message)
            }


        })
    }

    fun verifyAutentication() {
        val token = sharedPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val person = sharedPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        RetrofitClient.addHeaders(token, person)

        val logged = (token != "" && person != "")
       // _loggedUser.value = logged

        if (!logged){
            priorityRepository.list(object : APIListener<List<PriorityModel>> {
                override fun onSuccess(result: List<PriorityModel>) {
                    priorityRepository.save(result)
                }

                override fun onFailure(message: String) {
                    null
                }

            })
        }

        _loggedUser.value = (logged && BiometricHelper.isBiometricAvalible(getApplication()))

    }
}