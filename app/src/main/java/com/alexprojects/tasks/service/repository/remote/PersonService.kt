package com.alexprojects.tasks.service.repository.remote

import com.alexprojects.tasks.service.model.PersonModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PersonService {

    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(
        @Field("email")
        email: String,

        @Field("password")
        password: String
    ): Call<PersonModel>

    @POST("Authentication/Create")
    fun create(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<PersonModel>

}