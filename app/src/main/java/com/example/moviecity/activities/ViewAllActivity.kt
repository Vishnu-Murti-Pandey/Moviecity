package com.example.moviecity.activities

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviecity.R
import com.example.moviecity.databinding.ActivityViewAllBinding
import com.example.moviecity.fragments.PopularMovieFragment
import com.example.moviecity.fragments.TopRatedMovieFragment
import com.example.moviecity.fragments.UpcomingMovieFragment

class ViewAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllBinding
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras

        fragment = when (bundle?.getString("frag")) {
            "1" -> {
                PopularMovieFragment()
            }
            "2" -> {
                UpcomingMovieFragment()
            }
            else -> {
                TopRatedMovieFragment()
            }
        }

        replaceFragment()

    }

    private fun replaceFragment() {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container, fragment)
        fragmentTransaction.commit()
        Log.d(ContentValues.TAG, "Popular Clicked")
    }


}