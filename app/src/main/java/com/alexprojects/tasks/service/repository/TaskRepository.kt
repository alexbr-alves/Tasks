package com.alexprojects.tasks.service.repository

import android.content.Context
import com.alexprojects.tasks.R
import com.alexprojects.tasks.service.constants.TaskConstants
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.TaskModel
import com.alexprojects.tasks.service.repository.remote.RetrofitClient
import com.alexprojects.tasks.service.repository.remote.TaskService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context): BaseRepository() {

    val remote = RetrofitClient.getService(TaskService::class.java)

    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        val call = remote.create(task.priority, task.description, task.dueDate, task.complete)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                handleResponse(response, listener)
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(R.string.ERROR_UNEXPECTED.toString())
            }

        })
    }
}