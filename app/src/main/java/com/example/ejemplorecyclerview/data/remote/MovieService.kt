package com.example.ejemplorecyclerview.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieService {

    @GET("discover/movie")
    suspend fun getMovies(@QueryMap queries: Map<String, String>): Response<MovieResponse>

    @GET("search/movie")
    suspend fun findMovie(@QueryMap queries: Map<String, String>): Response<MovieResponse>

}