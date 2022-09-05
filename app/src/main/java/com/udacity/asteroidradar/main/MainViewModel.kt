package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.Database.DatabaseDao
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.DataAsteroidApi
import com.udacity.asteroidradar.api.ImgAsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel(application: Application) : ViewModel() {

//    private val _imgResponse = MutableLiveData<PictureOfDay>()
//    val imgResponse: LiveData<PictureOfDay>
//        get() = _imgResponse


    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetails: MutableLiveData<Asteroid?>
        get() = _navigateToAsteroidDetails

    private val database = AsteroidDatabase.getInstance(application)

    private val repository = database?.let { AsteroidRepository(it) }



    fun onAsteroidListenerClicked(asteroid: Asteroid){
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onAsteroidDetailsNavigated(){
        _navigateToAsteroidDetails.value = null
    }

    init {
//        getPhotoFromApi()

        viewModelScope.launch {

            repository!!.refreshAsteroids()
            repository.refreshPhoto()
        }


    }

    val asteroidList = repository!!.asteroids
    val pictureOfDay = repository?.pictureOfDay

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