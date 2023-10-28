package com.alexprojects.tasks.service.repository.remote

import com.alexprojects.tasks.service.model.PriorityModel
import retrofit2.Call
import retrofit2.http.GET

interface PriorityService {

    @GET("Priority")
    fun list(): Call<List<PriorityModel>>
}