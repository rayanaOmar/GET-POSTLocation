package com.example.getandpostlocation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {

    @GET("/test/")
    fun getUser(): Call<Array<Users>>

    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUser(@Body userData: Users): Call<Users>

}