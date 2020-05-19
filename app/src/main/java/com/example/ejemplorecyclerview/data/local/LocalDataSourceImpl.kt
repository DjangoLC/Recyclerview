package com.example.ejemplorecyclerview.data.local

import com.example.ejemplorecyclerview.data.LocalDataSource
import com.example.ejemplorecyclerview.data.Movie

import com.example.ejemplorecyclerview.data.local.Movie as MovieDb

class LocalDataSourceImpl(private val movieDao: MovieDao) : LocalDataSource {

    override fun save(movies: List<MovieDb>) {
        movieDao.insertMovies(movies)
    }

    override fun getNewMovies(year: Int,sortBy: String): List<MovieDb> {
        return movieDao.getNewsMovies("%$year%")
    }

    override fun getPopularMovies(): List<com.example.ejemplorecyclerview.data.local.Movie> {
        return movieDao.getPopularMovies()
    }

    override fun getUpcomingMovies(): List<com.example.ejemplorecyclerview.data.local.Movie> {
        return movieDao.getUpcomingMovies()
    }

    override fun getMovieById(movieId: Int): MovieDb {
        return movieDao.findById(movieId)
    }

    override fun find(movieName: String): List<MovieDb> {
        return movieDao.findMovie("%$movieName%")
    }
}