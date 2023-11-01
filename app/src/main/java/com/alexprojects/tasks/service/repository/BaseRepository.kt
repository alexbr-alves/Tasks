package com.alexprojects.tasks.service.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

    fun isConnectionAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNet = cm.activeNetwork ?: return false
            val networkCapabilities = cm.getNetworkCapabilities(activeNet) ?: return false

            result = when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            if (cm.activeNetworkInfo != null) {
                result = when (cm.activeNetworkInfo!!.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }
}