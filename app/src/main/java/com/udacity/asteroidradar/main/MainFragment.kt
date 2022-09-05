package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(activity).application
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = MainViewModelFactory(application)

        viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]

        binding.viewModel = viewModel



        val adapter = MainAdapter(AsteroidClickListener {
            asteroid -> viewModel.onAsteroidListenerClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onAsteroidDetailsNavigated()
            }
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_today_asteroids -> viewModel.updateFilter(AsteroidFilter.TODAY_ASTEROIDS)
            R.id.show_week_asteroids -> viewModel.updateFilter(AsteroidFilter.WEEKLY_ASTEROIDS)
            R.id.show_saved_asteroids -> viewModel.updateFilter(AsteroidFilter.SAVED_ASTEROIDS)

        }
        return true
    }
}
