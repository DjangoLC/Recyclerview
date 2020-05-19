package com.example.ejemplorecyclerview.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplorecyclerview.data.Movie
import com.example.ejemplorecyclerview.R
import com.example.ejemplorecyclerview.utils.loadUrl
import kotlinx.android.synthetic.main.row.view.*
import java.util.*
import kotlin.properties.Delegates

typealias onClick = (onMovieClick: Movie) -> Unit

class RecyclerAdapter(private val onMovieClick: onClick, private val onFavClick: onClick) :
    RecyclerView.Adapter<RecyclerAdapter.RecyclerviewAndroid>(), Filterable {

    var items: List<Movie> by Delegates.observable(emptyList()) { _, _, new ->
        items_filter = new
        notifyDataSetChanged()
    }

    var items_filter = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAndroid {
        return RecyclerviewAndroid.fromParent(
            parent
        )
    }

    override fun getItemCount(): Int {
        return items_filter.size
    }

    override fun onBindViewHolder(holder: RecyclerviewAndroid, position: Int) {
        holder.bind(items_filter[position], onMovieClick, onFavClick)
    }

    class RecyclerviewAndroid(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie, onMovie: onClick, onFav: onClick) {
            with(itemView) {
                img.loadUrl(movie.url)
                tvTitle.text = movie.name
                setOnClickListener {
                    onMovie(movie)
                }
                favorite.setOnClickListener {
                    onFav(movie)
                }
            }
        }

        companion object {

            fun fromParent(parent: ViewGroup): RecyclerviewAndroid {
                return RecyclerviewAndroid(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.row,
                            parent,
                            false
                        )
                )
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val results = Filter.FilterResults()

                results.values = if (query == null || query.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.name.toLowerCase(Locale("es_MX")).contains(query)
                    }
                }

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items_filter = results?.values as List<Movie>
                notifyDataSetChanged()
            }
        }
    }

}