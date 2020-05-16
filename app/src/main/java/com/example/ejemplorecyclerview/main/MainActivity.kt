package com.example.ejemplorecyclerview.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplorecyclerview.R
import com.example.ejemplorecyclerview.data.Movie
import com.example.ejemplorecyclerview.data.MovieRepository
import com.example.ejemplorecyclerview.data.NetworkServiceImpl
import com.example.ejemplorecyclerview.data.local.LocalDataSourceImpl
import com.example.ejemplorecyclerview.data.local.MovieDatabase
import com.example.ejemplorecyclerview.data.remote.RemoteDataSourceImpl
import com.example.ejemplorecyclerview.data.remote.Retrofit
import com.example.ejemplorecyclerview.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var repository: MovieRepository

    private val mAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter({ navigateTo(it.id) }, {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        repository = MovieRepository(
            NetworkServiceImpl(this),
            LocalDataSourceImpl(MovieDatabase.build(this).movieDao()),
            RemoteDataSourceImpl(Retrofit.getMovieService())
        )

        setUpAdapter()
        setUpVies()


    }

    private fun setUpVies() {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipNew -> {
                    getNewMovies()
                }

                R.id.chipPopular -> {
                    mAdapter.items = emptyList()
                }

                R.id.chipUpcoming -> {
                    mAdapter.items = emptyList()
                }
            }
        }
    }


    private fun navigateTo(id: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE, id)
        startActivity(intent)
    }

    private fun getNewMovies() {
        GlobalScope.launch {
            val movies = repository.getMoviesBy(
                "e462893643adb76db04afff8aa0f30c0",
                "es-MX",
                1,
                "release_date.desc",
                2020
            )
            withContext(Dispatchers.Main) {
                setUpMovies(movies)
            }
        }
    }


    private fun setUpMovies(movies: List<Movie>) {
        mAdapter.items = movies
    }

    private fun setUpAdapter() {
        getNewMovies()
        mRecyclerview.apply {
            adapter = mAdapter
        }
    }


}
