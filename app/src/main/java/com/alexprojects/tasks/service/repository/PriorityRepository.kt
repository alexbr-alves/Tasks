package com.alexprojects.tasks.service.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alexprojects.tasks.R
import com.alexprojects.tasks.service.listener.APIListener
import com.alexprojects.tasks.service.model.PriorityModel
import com.alexprojects.tasks.service.repository.local.TaskDatabase
import com.alexprojects.tasks.service.repository.remote.PriorityService
import com.alexprojects.tasks.service.repository.remote.RetrofitClient

class PriorityRepository(val context: Context): BaseRepository() {

    private val remote = RetrofitClient.getService(PriorityService::class.java)
    private val database = TaskDatabase.getDatabase(context).priorityDAO()

    fun list(listener: APIListener<List<PriorityModel>>) {
        if (!isConnectionAvailable(context)) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
        }

        executeCall(remote.list(), listener)
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