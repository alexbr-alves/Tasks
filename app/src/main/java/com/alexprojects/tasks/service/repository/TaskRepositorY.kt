package com.alexprojects.tasks.service.repository

import com.alexprojects.tasks.service.repository.remote.RetrofitClient
import com.alexprojects.tasks.service.repository.remote.TaskService

class TaskRepositorY {

    val remote = RetrofitClient.getService(TaskService::class.java)
}