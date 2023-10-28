package com.alexprojects.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PriorityModel
import com.alexprojects.tasks.service.model.TaskModel
import com.alexprojects.tasks.service.repository.PriorityRepository
import com.alexprojects.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val taskRepository = TaskRepository(application.applicationContext)

    private val _priorityList = MutableLiveData<List<PriorityModel>>()
    val priorityList: LiveData<List<PriorityModel>> = _priorityList

    fun save(task: TaskModel) {
        taskRepository.create(task, object : APIListener<Boolean>{
            override fun onSuccess(result: Boolean) {
               val s = ""
            }

            override fun onFailure(message: String) {
                val f = ""
            }

        })
    }
    fun loadPriorities() {
        _priorityList.value = priorityRepository.list()
    }

}