package com.branovitski.chililab.network

import com.branovitski.chililab.model.GifResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GIFApiService {

    @GET("gifs/trending?")
    suspend fun getGifs(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<GifResponse>

    @GET("gifs/search?rating=g&lang=en")
    suspend fun getSearchedGifs(
        @Query("q") searchQuery: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<GifResponse>
}