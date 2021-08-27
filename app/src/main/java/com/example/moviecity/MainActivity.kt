package com.example.moviecity

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecity.activities.*
import com.example.moviecity.adapters.*
import com.example.moviecity.dataBase.MoviesDatabase
import com.example.moviecity.databinding.ActivityMainBinding
import com.example.moviecity.models.PopularMovies
import com.example.moviecity.models.TopRatedMovies
import com.example.moviecity.models.UpcomingMovies
import com.example.moviecity.networkHelper.NetworkChecker
import com.example.moviecity.networkRequest.MovieService
import com.example.moviecity.repository.MainPopularRepo
import com.example.moviecity.repository.MainTopRatedRepo
import com.example.moviecity.repository.MainUpcomingRepo
import com.example.moviecity.viewModel.MainViewModel
import com.example.moviecity.viewModel.viewModelFactory.MainVMFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(), PopularMovieItemClicked, UpcomingMovieItemClicked,
    TopRatedMovieItemClicked {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    private lateinit var popularMovieAdapter: PopularMoviesAdapter
    private lateinit var upcomingMovieAdapter: UpcomingMoviesAdapter
    private lateinit var topRatedMovieAdapter: TopRatedMoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        if (auth.currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val popularDao = MoviesDatabase.getDatabase(applicationContext).popularDao()
        val upcomingDao = MoviesDatabase.getDatabase(applicationContext).upcomingDao()
        val topRatedDao = MoviesDatabase.getDatabase(applicationContext).topRatedDao()

        val popularRepo = MainPopularRepo(popularDao, apiInstance = MovieService)
        val upcomingRepo = MainUpcomingRepo(upcomingDao, apiInstance = MovieService)
        val topRatedRepo = MainTopRatedRepo(topRatedDao, apiInstance = MovieService)


        val mainViewModel: MainViewModel = ViewModelProvider(
            this,
            MainVMFactory(popularRepo, upcomingRepo, topRatedRepo)
        ).get(MainViewModel::class.java)


        binding.recyclerViewPopularMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setAdapterPopular(mainViewModel)
        popularMovieAdapter = PopularMoviesAdapter(this)
        binding.recyclerViewPopularMovie.adapter = popularMovieAdapter


        binding.recyclerViewUpcomingMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setAdapterUpcoming(mainViewModel)
        upcomingMovieAdapter = UpcomingMoviesAdapter(this)
        binding.recyclerViewUpcomingMovie.adapter = upcomingMovieAdapter


        binding.recyclerViewTopRatedMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setAdapterTopRated(mainViewModel)
        topRatedMovieAdapter = TopRatedMoviesAdapter(this)
        binding.recyclerViewTopRatedMovie.adapter = topRatedMovieAdapter


        openPopularViewAll()
        openUpcomingViewAll()
        openTopRatedViewAll()

        binding.searchBar.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.menu.setOnClickListener {
            showPopup(it)
        }

    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.saved -> {
                    startActivity(Intent(this, FavouriteActivity::class.java))
                }
                R.id.sign_out -> {
                    signOut()
                }
            }
            true
        }
        popup.show()
    }


    private fun setAdapterPopular(mainViewModel: MainViewModel) {
        if (!NetworkChecker.isNetworkAvailable(applicationContext)) {
            mainViewModel.getPopularData().observe(this, {
                popularMovieAdapter.updatePopularMovie(it.results)
            })
        } else {
            initPopularViewModel(mainViewModel)
        }
    }

    private fun setAdapterUpcoming(mainViewModel: MainViewModel) {
        if (!NetworkChecker.isNetworkAvailable(applicationContext)) {
            mainViewModel.getUpcomingData().observe(this, {
                upcomingMovieAdapter.updateUpcomingMovie(it.results)
            })
        } else {
            initUpcomingViewModel(mainViewModel)
        }
    }

    private fun setAdapterTopRated(mainViewModel: MainViewModel) {
        if (!NetworkChecker.isNetworkAvailable(applicationContext)) {
            mainViewModel.getTopRatedData().observe(this, {
                topRatedMovieAdapter.updateTopRatedMovie(it.results)
            })
        } else {
            initTopRatedViewModel(mainViewModel)
        }
    }


    private fun initPopularViewModel(viewModel: MainViewModel) {

        viewModel.getPopularMovies()

        viewModel.popularMovieLiveDataObserver().observe(this, {
            if (it != null) {
                popularMovieAdapter.updatePopularMovie(it.results)
                viewModel.insertPopularData(it)

            } else {
                Toast.makeText(this, "Error In Fetching data...", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUpcomingViewModel(viewModel: MainViewModel) {

        viewModel.getUpcomingMovies()

        viewModel.upcomingMovieLiveDataObserver().observe(this, {
            if (it != null) {
                upcomingMovieAdapter.updateUpcomingMovie(it.results)
                viewModel.insertUpcomingData(it)
            } else {
                Toast.makeText(this, "Error In Fetching data...", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initTopRatedViewModel(viewModel: MainViewModel) {

        viewModel.getTopRatedMovies()

        viewModel.topRatedMovieLiveDataObserver().observe(this, {
            if (it != null) {
                topRatedMovieAdapter.updateTopRatedMovie(it.results)
                viewModel.insertTopRatedData(it)
            } else {
                Toast.makeText(this, "Error In Fetching data...", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun openPopularViewAll() {
        binding.tvBtPopularViewAll.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            intent.putExtra("frag", "1")
            startActivity(intent)
        }
    }

    private fun openTopRatedViewAll() {
        binding.tvBtTopRatedViewAll.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            intent.putExtra("frag", "3")
            startActivity(intent)
        }
    }

    private fun openUpcomingViewAll() {
        binding.tvBtUpcomingViewAll.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            intent.putExtra("frag", "2")
            startActivity(intent)
        }

    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onPopularMovieItemClicked(item: PopularMovies) {
        if (NetworkChecker.isNetworkAvailable(applicationContext)) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("movieId", item.id)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No Internet!!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUpcomingMovieItemClicked(item: UpcomingMovies) {
        if (NetworkChecker.isNetworkAvailable(applicationContext)) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("movieId", item.id)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No Internet!!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTopRatedMovieItemClicked(item: TopRatedMovies) {
        if (NetworkChecker.isNetworkAvailable(applicationContext)) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("movieId", item.id)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No Internet!!!", Toast.LENGTH_SHORT).show()
        }
    }
}