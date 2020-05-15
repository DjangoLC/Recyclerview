package com.example.ejemplorecyclerview.data

class Movie(val name: String, val url: String) {

    companion object {

        fun getItems() = listOf(
            Movie(
                "Movie 1",
                "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"
            ),
            Movie(
                "Movie 2",
                "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"
            ),
            Movie(
                "Movie 3",
                "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"
            ),
            Movie(
                "Movie 4",
                "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"
            )
        )

    }

}