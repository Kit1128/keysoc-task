package com.example.keysoccodingtask.retrofit.request

import com.example.keysoccodingtask.retrofit.model.AlbumResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {
    @GET("search")
    suspend fun getAlbums(
        @Query("term") searchTerm: String,
        @Query("entity") entity: String
    ): Response<AlbumResult>
}