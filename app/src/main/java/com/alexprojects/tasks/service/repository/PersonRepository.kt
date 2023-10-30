package com.alexprojects.tasks.service.repository

import android.content.Context
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PersonModel
import com.alexprojects.tasks.service.repository.remote.PersonService
import com.alexprojects.tasks.service.repository.remote.RetrofitClient

class PersonRepository(val context: Context): BaseRepository() {

    private val remote = RetrofitClient.getService(PersonService::class.java)
    fun login(email: String, password: String, listener: APIListener<PersonModel>) {
        executeCall(remote.login(email, password), listener)
    }

}