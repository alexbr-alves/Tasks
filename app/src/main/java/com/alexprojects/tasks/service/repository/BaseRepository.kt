package com.alexprojects.tasks.service.repository

import com.alexprojects.tasks.R
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.listener.APIListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository() {

    fun<T> executeCall(call: Call<T>, listener: APIListener<T>){
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(Gson().fromJson(response.errorBody()!!.string(), String::class.java))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                listener.onFailure(R.string.ERROR_UNEXPECTED.toString())
            }
        })
    }
}