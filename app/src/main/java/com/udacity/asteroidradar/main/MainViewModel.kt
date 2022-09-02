package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ImgAsteroidApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _imgResponse = MutableLiveData<PictureOfDay>()
    val imgResponse: LiveData<PictureOfDay>
    get() = _imgResponse

    private val _dataResponse = MutableLiveData<List<Asteroid>>()
    val dataResponse: LiveData<List<Asteroid>>
    get() = _dataResponse

    init {
        getPhotoFromApi()
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