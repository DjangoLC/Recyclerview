package com.example.ejemplorecyclerview.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    @PrimaryKey val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val favorite: Boolean
)