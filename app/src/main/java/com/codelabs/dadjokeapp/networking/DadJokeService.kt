package com.codelabs.dadjokeapp.networking

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface DadJokeService {

    @GET("/")
    @Headers(
        "Accept: application/json",
    )
    fun getRandomDadJoke(): Call<DadJokeResponse>

    @GET("/")
    @Headers(
        "Accept: application/json",
    )
    fun singleRandomDadJoke(): Single<DadJokeResponse>

}