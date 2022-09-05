package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class AsteroidFilter {
    TODAY_ASTEROIDS,
    WEEKLY_ASTEROIDS,
    SAVED_ASTEROIDS
}

class MainViewModel(application: Application) : ViewModel() {

//    private val _imgResponse = MutableLiveData<PictureOfDay>()
//    val imgResponse: LiveData<PictureOfDay>
//        get() = _imgResponse


    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetails: MutableLiveData<Asteroid?>
        get() = _navigateToAsteroidDetails

    private val database = AsteroidDatabase.getInstance(application)

    private val repository = database?.let { AsteroidRepository(it) }





    private val filter =
        MutableLiveData(AsteroidFilter.SAVED_ASTEROIDS)


    val asteroidList = Transformations.switchMap(filter) {
        when (it) {
            AsteroidFilter.TODAY_ASTEROIDS -> repository?.currentDayAsteroids
            AsteroidFilter.WEEKLY_ASTEROIDS -> repository?.weeklyAsteroids
            else -> repository?.allAsteroid
        }
    }






    init {
//        getPhotoFromApi()

        viewModelScope.launch {

            repository!!.refreshAsteroids()
            repository.refreshPhoto()
        }

    }


    val pictureOfDay = repository?.pictureOfDay

    fun updateFilter(asteroidFilter: AsteroidFilter){
        filter.value = asteroidFilter
    }

    fun onAsteroidListenerClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onAsteroidDetailsNavigated() {
        _navigateToAsteroidDetails.value = null
    }

//    private fun getPhotoFromApi() {
//        viewModelScope.launch {
//            try {
//                _imgResponse.value = ImgAsteroidApi.retrofitService.getPhoto()
//                Log.i("ApiAsteroid", _imgResponse.value!!.url)
//            }catch (e: Exception){
//                e.message?.let { Log.i("ApiAsteroid", it) }
//            }
//        }
//    }
}