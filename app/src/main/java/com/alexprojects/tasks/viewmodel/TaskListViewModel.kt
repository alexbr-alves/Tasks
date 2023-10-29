package com.alexprojects.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.TaskModel
import com.alexprojects.tasks.service.repository.TaskRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application.applicationContext)

    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    fun list() {
        taskRepository.list(object : APIListener<List<TaskModel>> {
            override fun onSuccess(result: List<TaskModel>) {
                _tasks.value = result
            }

            override fun onFailure(message: String) {
                TODO("Not yet implemented")
            }

        })
    }

}