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
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel : ViewModel() {

    private val _imgResponse = MutableLiveData<PictureOfDay>()
    val imgResponse: LiveData<PictureOfDay>
        get() = _imgResponse

    private val _dataResponse = MutableLiveData<List<Asteroid>>()
    val dataResponse: LiveData<List<Asteroid>>
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
                val data:String = DataAsteroidApi.retrofitService.getData()
                val json = JSONObject(data)
                _dataResponse.value = parseAsteroidsJsonResult(json)
                Log.i("dataSize", _dataResponse.value!!.size.toString())

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