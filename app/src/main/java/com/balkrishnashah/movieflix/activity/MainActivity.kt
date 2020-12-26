package com.balkrishnashah.movieflix.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balkrishnashah.movieapp.adapters.MovieAdapter
import com.balkrishnashah.movieflix.R
import com.balkrishnashah.movieflix.data.Movie

class MainActivity : AppCompatActivity(), MainActivityContract.view {

    //for popular movies
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private var popularMoviesPage = 1

    //for top rated movies
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MovieAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private var topRatedMoviesPage = 1

    //for upcoming movies
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MovieAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    private var upcomingMoviesPage = 1


//    private val movieDataIntractor: MovieDataIntractor? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        init_components();
        init_recyclerView();

    }
    private fun init_components() {
        popularMovies = findViewById(R.id.popular_movies)
        topRatedMovies = findViewById(R.id.top_rated_movies)
        upcomingMovies = findViewById(R.id.upcoming_movies)
    }

    private fun init_recyclerView(){
        init_popular_movie_recyler()
        init_top_rated_movie_recycler()
        init_upcoming_movie_recycler()
    }

    private fun init_popular_movie_recyler(){
        popularMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MovieAdapter(mutableListOf()){
            Movie -> showMovieDetail(Movie)
        }
        popularMovies.adapter = popularMoviesAdapter
        getPopularMovies()

    }
    private fun init_top_rated_movie_recycler(){
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MovieAdapter(mutableListOf())
        {
                Movie -> showMovieDetail(Movie)
        }
        topRatedMovies.adapter = topRatedMoviesAdapter

        getTopRatedMovies()

    }
    private fun init_upcoming_movie_recycler(){

        upcomingMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MovieAdapter(mutableListOf()) {
                Movie -> showMovieDetail(Movie)
        }
        upcomingMovies.adapter = upcomingMoviesAdapter

        getUpcomingMovies()

    }

    override fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    override fun getPopularMovies() {

        MovieDataIntractor.getPopularMovies(
            popularMoviesPage,
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    override fun getTopRatedMovies() {

        MovieDataIntractor.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    override fun getUpcomingMovies() {
        MovieDataIntractor.getUpcomingMovies(
            upcomingMoviesPage,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    override fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    override fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)

        attachTopRatedMoviesOnScrollListener()
    }

    override fun onUpcomingMoviesFetched(movies: List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }

    override fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    override fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    override fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun showMovieDetail(movie: Movie) {

        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)

    }
}