package com.alexprojects.tasks.service.repository.remote

import com.alexprojects.tasks.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    companion object {
        private lateinit var INSTANCE: Retrofit
        private  var token: String = ""
        private  var personKey: String = ""

        private fun getRetrofitInstance(): Retrofit {
            var httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(TaskConstants.HEADER.TOKEN_KEY, token)
                    .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
                    .build()
                chain.proceed(request)
            }

            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl("http://devmasterteam.com/CursoAndroidAPI/")
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }

        fun <T> getService(serviceClass: Class<T>) : T {
            return getRetrofitInstance().create(serviceClass)
        }

        fun addHeaders(tokenValue: String, personKeyValue: String){
            token = tokenValue
            personKey = personKeyValue
        }
    }

}