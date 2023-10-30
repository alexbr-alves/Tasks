package com.alexprojects.tasks.service.repository

import android.content.Context
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.TaskModel
import com.alexprojects.tasks.service.repository.remote.RetrofitClient
import com.alexprojects.tasks.service.repository.remote.TaskService
import retrofit2.Call

class TaskRepository(val context: Context): BaseRepository() {

    private val remote = RetrofitClient.getService(TaskService::class.java)

    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        val call = remote.create(task.priority, task.description, task.dueDate, task.complete)
        executeCall(call, listener)
    }

    fun list(listener: APIListener<List<TaskModel>>) {
        executeCall(remote.list(), listener)
    }

    fun listNext(listener: APIListener<List<TaskModel>>) {
        list(remote.listNext(), listener)
    }

    fun listOverDuo(listener: APIListener<List<TaskModel>>) {
        executeCall(remote.listOverDue(), listener)
    }

    private fun list(call: Call<List<TaskModel>>, listener: APIListener<List<TaskModel>>) {
        executeCall(call, listener)
    }

    fun delete(id: Int, listener: APIListener<Boolean>) {
        executeCall(remote.delete(id), listener)
    }

    fun status(id: Int, complete: Boolean, listener: APIListener<Boolean>) {
        if (complete) {
            executeCall(remote.complete(id), listener)
        } else {
            executeCall(remote.undo(id), listener)
        }
    }
}