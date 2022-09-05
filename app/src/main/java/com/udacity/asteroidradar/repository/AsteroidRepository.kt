package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.DataAsteroidApi
import com.udacity.asteroidradar.api.ImgAsteroidApi

import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    val pictureOfDay: LiveData<PictureOfDay> = database.picDatabaseDo.getPicture()



    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

            try {
                val responseString = DataAsteroidApi.retrofitService.getData()
                val json = JSONObject(responseString)
                val networkAsteroid = parseAsteroidsJsonResult(json)
                networkAsteroid.forEach(){
                    database.databaseDao.insert(it)

                }
                Log.i("Asteroid list size: ",networkAsteroid.size.toString())
            } catch (e: Exception){
                Log.i("Error ya talaat", e.message.toString())
            }

        }
    }
    suspend fun refreshPhoto(){
        withContext(Dispatchers.IO){
            try {
                val response = ImgAsteroidApi.retrofitService.getPhoto()
                database.picDatabaseDo.insertPicture(response)
            }catch (e:Exception){
                e.message?.let { Log.i("Failed ya talaat", it) }
            }
        }
    }

}
