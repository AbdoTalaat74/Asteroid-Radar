package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.DataAsteroidApi
import com.udacity.asteroidradar.api.ImgAsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _imgResponse = MutableLiveData<PictureOfDay>()
    val imgResponse: LiveData<PictureOfDay>
        get() = _imgResponse

    private val _dataResponse = MutableLiveData<JSONObject>()
    val dataResponse: LiveData<JSONObject>
        get() = _dataResponse

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetails: MutableLiveData<Asteroid?>
        get() = _navigateToAsteroidDetails

    fun onAsteroidListenerClicked(asteroid: Asteroid){
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onAsteroidDetailsNavigated(){
        _navigateToAsteroidDetails.value = null
    }

    init {
        getPhotoFromApi()
        viewModelScope.launch {
            try {

                _dataResponse.value = DataAsteroidApi.retrofitService.getData()
                Log.i("dataResponse", _dataResponse.value!!.toString())
            } catch (e: Exception) {
                e.message?.let {
                    Log.i("ApiAsteroid", it)
                }
            }
        }
    }

    private fun getPhotoFromApi() {
        viewModelScope.launch {
            try {

                _imgResponse.value = ImgAsteroidApi.retrofitService.getPhoto()
                Log.i("ApiAsteroid", _imgResponse.value!!.url)
            }catch (e: Exception){
                e.message?.let { Log.i("ApiAsteroid", it) }
            }
        }
    }
}