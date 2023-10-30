package com.alexprojects.tasks.service.repository

import android.content.Context
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PriorityModel
import com.alexprojects.tasks.service.repository.local.TaskDatabase
import com.alexprojects.tasks.service.repository.remote.PriorityService
import com.alexprojects.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(val context: Context): BaseRepository() {

    private val remote = RetrofitClient.getService(PriorityService::class.java)
    private val database = TaskDatabase.getDatabase(context).priorityDAO()

    fun list(listener: APIListener<List<PriorityModel>>) {
        val call = remote.list()
        call.enqueue(object : Callback<List<PriorityModel>> {
            override fun onResponse(
                call: Call<List<PriorityModel>>,
                response: Response<List<PriorityModel>>
            ) {
                handleResponse(response, listener)
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                onFailure(listener)
            }

        })
    }

    fun list(): List<PriorityModel> {
        return database.list()
    }

    fun getDescription(id: Int): String {
        val cached = PriorityRepository.getDescription(id)
        return if (cached == "") {
            val description = database.getDescription(id)
            setDescription(id, description)
            description
        } else {
            cached
        }
    }

    fun save(list: List<PriorityModel>) {
        database.clear()
        database.save(list)
    }

    companion object {
        private val cache = mutableMapOf<Int, String>()
        fun getDescription(id: Int): String {
            return cache[id] ?: ""
        }
        fun setDescription(id: Int, str: String) {
            cache[id] = str
        }
    }

}