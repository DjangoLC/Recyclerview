package com.example.ejemplorecyclerview.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplorecyclerview.data.Movie
import com.example.ejemplorecyclerview.R
import com.example.ejemplorecyclerview.data.MovieRepository
import com.example.ejemplorecyclerview.data.NetworkServiceImpl
import com.example.ejemplorecyclerview.data.local.LocalDataSourceImpl
import com.example.ejemplorecyclerview.data.local.MovieDatabase
import com.example.ejemplorecyclerview.data.remote.RemoteDataSourceImpl
import com.example.ejemplorecyclerview.data.remote.Retrofit
import com.example.ejemplorecyclerview.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var repository: MovieRepository

    private val mAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter(
            {
                toast("view detail: ${it.name}")
                Log.e("asd","${it.id}")
            }, {
                toast("favorite: ${it.name}")
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        repository = MovieRepository(
            NetworkServiceImpl(),
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
                    toast("popular selected")
                    mAdapter.items = emptyList()
                }

                R.id.chipUpcoming -> {
                    toast("upcoming selected")
                    mAdapter.items = emptyList()
                }
            }
        }
    }

    private fun getNewMovies() {
        GlobalScope.launch {
            val movies = repository.getMoviesBy("e462893643adb76db04afff8aa0f30c0","es",1, "popularity.desc", 2020)
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
