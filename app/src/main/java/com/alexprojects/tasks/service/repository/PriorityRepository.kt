package com.alexprojects.tasks.service.repository

import android.content.Context
import com.alexprojects.tasks.R
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PriorityModel
import com.alexprojects.tasks.service.repository.remote.PriorityService
import com.alexprojects.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context) {

    private val remote = RetrofitClient.getService(PriorityService::class.java)

    fun list(listener: APIListener<List<PriorityModel>>) {
        val call = remote.list()
        call.enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(failResponse(response.errorBody()!!.string()))
                }
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                listener.onFailure(R.string.ERROR_UNEXPECTED.toString())
            }

        })
    }

    private fun failResponse(str: String): String {
        return Gson().fromJson(str, String::class.java)
    }
}