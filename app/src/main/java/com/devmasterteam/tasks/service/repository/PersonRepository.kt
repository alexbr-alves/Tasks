package com.devmasterteam.tasks.service.repository

import android.content.Context
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.service.constants.TaskConstants
import com.devmasterteam.tasks.service.listener.APIListener
import com.devmasterteam.tasks.service.model.PersonModel
import com.devmasterteam.tasks.service.repository.remote.PersonService
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
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