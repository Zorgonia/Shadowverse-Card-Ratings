package com.kevin.funratings.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardApi {

    @GET("/")
    suspend fun getHomepage(@Query("clan") clan: String, @Query("lang") lang: String): Response<String>

    @GET("/card/{id}")
    suspend fun getCard(@Path("id") id: String, @Query("lang") lang: String): Response<String>

    @GET("/api/v1/deck/import")
    suspend fun getDeckLink(@Query("format") format: String = "json", @Query("deck_code") deck_code: String): Response<NetworkDeckImport>
}