package com.alexprojects.tasks.service.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alexprojects.tasks.R
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PersonModel
import com.alexprojects.tasks.service.repository.remote.PersonService
import com.alexprojects.tasks.service.repository.remote.RetrofitClient

class PersonRepository(val context: Context): BaseRepository() {

    private val remote = RetrofitClient.getService(PersonService::class.java)


    fun login(email: String, password: String, listener: APIListener<PersonModel>) {
        if (!isConnectionAvailable(context)) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        executeCall(remote.login(email, password), listener)
    }

    fun create(name: String, email: String, password: String, listener: APIListener<PersonModel>) {
        if (!isConnectionAvailable(context)) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        executeCall(remote.create(name, email, password), listener)
    }

}