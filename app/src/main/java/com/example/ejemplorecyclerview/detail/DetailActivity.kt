package com.example.ejemplorecyclerview.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplorecyclerview.R
import com.example.ejemplorecyclerview.data.MovieRepository
import com.example.ejemplorecyclerview.data.NetworkServiceImpl
import com.example.ejemplorecyclerview.data.local.LocalDataSourceImpl
import com.example.ejemplorecyclerview.data.local.MovieDatabase
import com.example.ejemplorecyclerview.data.remote.RemoteDataSourceImpl
import com.example.ejemplorecyclerview.data.remote.Retrofit
import com.example.ejemplorecyclerview.utils.loadOriginalUrl
import com.example.ejemplorecyclerview.utils.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:Id"
    }

    private lateinit var repository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val movieId = intent.getIntExtra(MOVIE, -1)

        repository = MovieRepository(
            NetworkServiceImpl(this),
            LocalDataSourceImpl(MovieDatabase.build(this).movieDao()),
            RemoteDataSourceImpl(Retrofit.getMovieService())
        )

        GlobalScope.launch {
            val movie = repository.getMoviesById(movieId)

            withContext(Dispatchers.Main) {
                movieDetailImage.loadOriginalUrl(movie.url)
                movieDetailSummary.text = movie.overview
                movieDetailToolbar.apply {
                    title = movie.name
                }
            }
        }

        movieDetailToolbar.setNavigationOnClickListener {
            finish()
        }

    }
}
