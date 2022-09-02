package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val listAsteroid: List<Asteroid> = listOf(
            Asteroid(
                1, "code1", "Approach1", 0.12,
                0.33, 0.44, 0.55, false
            ), Asteroid(
                2, "code2", "Approach2", 0.14,
                0.35, 0.46, 0.59, true
            ), Asteroid(

                        3, "code3", "Approach2", 0.17,
                0.25, 0.76, 0.89, true


            )
        )

        val adapter = MainAdapter()
        binding.asteroidRecycler.adapter = adapter

        adapter.submitList(listAsteroid)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
