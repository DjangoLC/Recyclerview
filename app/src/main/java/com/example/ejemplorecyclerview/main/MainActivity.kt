package com.example.ejemplorecyclerview.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplorecyclerview.data.Movie
import com.example.ejemplorecyclerview.R
import com.example.ejemplorecyclerview.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter(
            {
                toast("view detail: ${it.name}")
            }, {
                toast("favorite: ${it.name}")
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAdapter()
        setUpVies()
    }

    private fun setUpVies() {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipNew -> {
                    toast("new selected")
                }

                R.id.chipPopular -> {
                    toast("popular selected")
                }

                R.id.chipUpcoming -> {
                    toast("upcoming selected")
                }
            }
        }
    }

    private fun setUpAdapter() {
        mAdapter.items = (Movie.getItems())
        mRecyclerview.apply {
            adapter = mAdapter
        }
    }


}
