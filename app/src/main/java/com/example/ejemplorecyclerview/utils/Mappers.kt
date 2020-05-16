package com.example.ejemplorecyclerview.utils

import com.example.ejemplorecyclerview.data.Movie as MovieList
import com.example.ejemplorecyclerview.data.remote.Movie as MovieWs
import com.example.ejemplorecyclerview.data.local.Movie as MovieDb

fun MovieWs.toDb() =
    MovieDb(
        adult ?: false,
        backdrop_path ?: "",
        id ?: 0,
        original_language ?: "",
        original_title ?: "",
        overview ?: "",
        popularity ?: 0.0,
        poster_path ?: "",
        release_date ?: "",
        title ?: "",
        video ?: false,
        vote_average ?: 0.0,
        vote_count ?: 0,
        false
    )

fun MovieDb.toList() =
    MovieList(id,title,backdrop_path,overview)
