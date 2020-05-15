package com.example.ejemplorecyclerview.data.remote

import com.example.ejemplorecyclerview.data.RemoteDataSource
import com.example.ejemplorecyclerview.data.remote.Movie  as MovieWs

class RemoteDataSourceImpl(private val movieWs: MovieService) : RemoteDataSource {

    override suspend fun getMoviesBy(
        apikey: String,
        language: String,
        page: Int,
        sortBy: String,
        primaryReleaseYear: Int
    ): List<MovieWs> {
        val map = mapOf("api_key" to apikey,"language" to language,
            "page" to page.toString(), "sort_by" to sortBy,
            "primary_release_year" to primaryReleaseYear.toString(),
            "region" to "US"
        )

        val response = movieWs.getMovies(map)

        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else emptyList()
    }

}