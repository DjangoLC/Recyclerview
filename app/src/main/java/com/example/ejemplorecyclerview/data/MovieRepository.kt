package com.example.ejemplorecyclerview.data

import com.example.ejemplorecyclerview.utils.toDb
import com.example.ejemplorecyclerview.utils.toList
import com.example.ejemplorecyclerview.data.local.Movie as MovieDb
import com.example.ejemplorecyclerview.data.Movie as MovieList
import com.example.ejemplorecyclerview.data.remote.Movie  as MovieWs

class MovieRepository(
    private val networkService: NetworkService,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getMoviesBy(apiKey: String, language: String, page: Int, sortBy: String, primaryReleaseYear: Int
    ): List<MovieList> {

        if (networkService.isConnected()) {
            val movies = remoteDataSource.getMoviesBy(apiKey,language,page, sortBy, primaryReleaseYear)
            localDataSource.save(movies.map { it.toDb() })
        }
        return localDataSource.getNewMovies(primaryReleaseYear,sortBy)
            .map { it.toList() }
    }

    suspend fun getPopularMovies(apiKey: String, language: String, page: Int, sortBy: String, primaryReleaseYear: Int) : List<MovieList> {
        if (networkService.isConnected()) {
            val movies = remoteDataSource.getMoviesBy(apiKey,language,page, sortBy, primaryReleaseYear)
            localDataSource.save(movies.map { it.toDb() })
        }
        return localDataSource.getPopularMovies().map { it.toList() }
    }

    suspend fun getUpcomingMovies(apiKey: String, language: String, page: Int, sortBy: String) : List<MovieList> {
        if (networkService.isConnected()) {
            val movies = remoteDataSource.getMoviesBy(apiKey,language,page, sortBy)
            localDataSource.save(movies.map { it.toDb() })
        }
        return localDataSource.getUpcomingMovies().map { it.toList() }
    }

    fun getMoviesById(movieId: Int) : MovieList {
        return localDataSource.getMovieById(movieId).toList()
    }

}

interface NetworkService {
    fun isConnected(): Boolean
}

interface LocalDataSource {
    fun save(movies: List<MovieDb>)
    fun getNewMovies(year: Int,sortBy: String): List<MovieDb>
    fun getPopularMovies(): List<MovieDb>
    fun getUpcomingMovies(): List<MovieDb>
    fun getMovieById(movieId: Int): MovieDb
}

interface RemoteDataSource {
    suspend fun getMoviesBy(
        apiKey: String,
        language: String, page: Int, sortBy: String, primaryReleaseYear: Int
    ): List<MovieWs>

    suspend fun getMoviesBy(
        apiKey: String,
        language: String, page: Int, sortBy: String
    ): List<MovieWs>
}