package com.example.ejemplorecyclerview.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplorecyclerview.R
import com.example.ejemplorecyclerview.data.Movie
import com.example.ejemplorecyclerview.data.MovieRepository
import com.example.ejemplorecyclerview.data.NetworkService
import com.example.ejemplorecyclerview.data.NetworkServiceImpl
import com.example.ejemplorecyclerview.data.local.LocalDataSourceImpl
import com.example.ejemplorecyclerview.data.local.MovieDatabase
import com.example.ejemplorecyclerview.data.remote.RemoteDataSourceImpl
import com.example.ejemplorecyclerview.data.remote.Retrofit
import com.example.ejemplorecyclerview.detail.DetailActivity
import com.example.ejemplorecyclerview.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var repository: MovieRepository

    private val networkService : NetworkService by lazy {
        NetworkServiceImpl(this)
    }

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
                    getPopularMovies()
                }

                R.id.chipUpcoming -> {
                    getUpcomingMovies()
                }

            }
        }

        searchView.apply {
            findViewById<TextView>(R.id.search_src_text).setTextColor(Color.WHITE)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    search(query!!)
                    searchView.hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    search(newText!!)
                    return true
                }
            })
        }

    }

    private fun search(movieName: String) {
        GlobalScope.launch {
            val movies = repository.findMovie(
                "e462893643adb76db04afff8aa0f30c0",
                "es-MX",
                movieName,
                1
            )

            withContext(Dispatchers.Main) {
                setUpMovies(movies)
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

    private fun getPopularMovies() {
        GlobalScope.launch {
            val movies = repository.getPopularMovies(
                "e462893643adb76db04afff8aa0f30c0",
                "es-MX",
                1,
                "popularity.desc",
                2020
            )
            withContext(Dispatchers.Main) {
                setUpMovies(movies)
            }
        }
    }

    private fun getUpcomingMovies() {
        GlobalScope.launch {
            val movies = repository.getUpcomingMovies(
                "e462893643adb76db04afff8aa0f30c0",
                "es-MX",
                1,
                "primary_release_date.desc"
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
