package com.example.ejemplorecyclerview.data.remote

import com.example.ejemplorecyclerview.data.RemoteDataSource
import com.example.ejemplorecyclerview.data.remote.Movie  as MovieWs

class RemoteDataSourceImpl(private val movieWs: MovieService) : RemoteDataSource {

    override suspend fun getMoviesBy(
        apiKey: String,
        language: String,
        page: Int,
        sortBy: String,
        primaryReleaseYear: Int
    ): List<MovieWs> {
        val map = mapOf("api_key" to apiKey,"language" to language,
            "page" to page.toString(), "sort_by" to sortBy,
            "primary_release_year" to primaryReleaseYear.toString(),
            "region" to "MX"
        )

        val response = movieWs.getMovies(map)

        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else emptyList()
    }

    override suspend fun getMoviesBy(
        apiKey: String,
        language: String,
        page: Int,
        sortBy: String
    ): List<MovieWs> {


        val map = mapOf("api_key" to apiKey,"language" to language,
            "page" to page.toString(), "sort_by" to sortBy,
            "region" to "MX"
        )

        val response = movieWs.getMovies(map)

        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else emptyList()
    }

    override suspend fun findMovie(
        apiKey: String,
        language: String,
        movieName: String,
        page: Int
    ): List<MovieWs> {
        val map = mapOf("api_key" to apiKey,"language" to language,
            "query" to movieName, "page" to page.toString(),
            "region" to "MX"
        )

        val response = movieWs.findMovie(map)

        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else emptyList()
    }

}