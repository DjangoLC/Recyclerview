package com.example.ejemplorecyclerview.data.local

import com.example.ejemplorecyclerview.data.LocalDataSource

import com.example.ejemplorecyclerview.data.local.Movie as MovieDb

class LocalDataSourceImpl(private val movieDao: MovieDao) : LocalDataSource {

    override fun save(movies: List<MovieDb>) {
        movieDao.insertMovies(movies)
    }

    override fun getNewMovies(year: Int): List<MovieDb> {
        return movieDao.getNewsMovies("%$year%")
    }
}