package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.DataAsteroidApi
import com.udacity.asteroidradar.api.ImgAsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidDatabase) {

    private var today = ""
    private var next7Days = ""

    init {


        val dataFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        today = dataFormat.format(calendar.time)

        calendar.add(Calendar.DAY_OF_YEAR, 7)
        next7Days = dataFormat.format(calendar.time)
    }

    val allAsteroid: LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getAllAsteroids()) {
            it.asDomainModel()
        }

    val currentDayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getCurrentDayAsteroids(today)) {
            it.asDomainModel()
        }

    val weeklyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.databaseDao.getWeeklyAsteroids(today, next7Days)) {
            it.asDomainModel()
        }

    val pictureOfDay: LiveData<PictureOfDay> = database.picDatabaseDo.getPicture()


    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

            try {
                val responseString = DataAsteroidApi.retrofitService.getData()
                val json = JSONObject(responseString)
                val networkAsteroid = parseAsteroidsJsonResult(json)
                networkAsteroid.forEach() {
                    database.databaseDao.insert(it)

                }
                Log.i("Asteroid list size: ", networkAsteroid.size.toString())
            } catch (e: Exception) {
                Log.i("Error ya talaat", e.message.toString())
            }

        }
    }

    suspend fun refreshPhoto() {
        withContext(Dispatchers.IO) {
            try {
                val response = ImgAsteroidApi.retrofitService.getPhoto()
                database.picDatabaseDo.insertPicture(response)
            } catch (e: Exception) {
                e.message?.let { Log.i("Failed ya talaat", it) }
            }
        }
    }

}
