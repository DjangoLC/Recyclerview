package com.example.ejemplorecyclerview.data.local

import androidx.room.*
import com.example.ejemplorecyclerview.data.Movie
import com.example.ejemplorecyclerview.data.local.Movie as MovieDb

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieDb>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findById(id: Int): MovieDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieDb>)

    @Update
    fun updateMovie(movie: MovieDb)

    @Query("SELECT * FROM Movie WHERE favorite == 1")
    fun getFavoriteMovies(): List<MovieDb>

    @Query("SELECT * FROM Movie WHERE release_date like :year ORDER BY title")
    fun getNewsMovies(year: String): List<MovieDb>

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    fun getPopularMovies(): List<MovieDb>

    @Query("SELECT * FROM Movie ORDER BY release_date DESC")
    fun getUpcomingMovies(): List<MovieDb>

    @Query("SELECT * FROM Movie WHERE title LIKE :movieName")
    fun findMovie(movieName: String): List<MovieDb>

}