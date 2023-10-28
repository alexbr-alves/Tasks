package com.alexprojects.tasks.service.repository

import android.content.Context
import com.alexprojects.tasks.R
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PersonModel
import com.alexprojects.tasks.service.repository.remote.PersonService
import com.alexprojects.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    val remote = RetrofitClient.getService(PersonService::class.java)

    fun login(email: String, password: String, listener: APIListener<PersonModel>) {
        val call = remote.login(email, password)
        call.enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure(R.string.ERROR_UNEXPECTED.toString())
            }

        })

    }

    private fun failResponse(str: String): String {
        return  Gson().fromJson(str, String::class.java)
    }
}