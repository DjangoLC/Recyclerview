package com.example.ejemplorecyclerview.data

import com.example.ejemplorecyclerview.utils.toDb
import com.example.ejemplorecyclerview.data.local.Movie as MovieDb
import com.example.ejemplorecyclerview.data.Movie as MovieList
import com.example.ejemplorecyclerview.data.remote.Movie  as MovieWs

class MovieRepository(
    private val networkService: NetworkService,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getMoviesBy(apikey: String, language: String, page: Int, sortBy: String, primaryReleaseYear: Int
    ): List<MovieList> {

        if (networkService.isConnected()) {
            val movies = remoteDataSource.getMoviesBy(apikey,language,page, sortBy, primaryReleaseYear)
            localDataSource.save(movies.map { it.toDb() })
        }
        return localDataSource.getNewMovies(primaryReleaseYear)
            .map { MovieList(it.id,it.original_title, it.backdrop_path) }
    }

}

interface NetworkService {
    fun isConnected(): Boolean
}

interface LocalDataSource {
    fun save(movies: List<MovieDb>)
    fun getNewMovies(year: Int): List<MovieDb>
}

interface RemoteDataSource {
    suspend fun getMoviesBy(
        apikey: String,
        language: String, page: Int, sortBy: String, primaryReleaseYear: Int
    ): List<MovieWs>
}