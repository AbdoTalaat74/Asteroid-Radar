package com.udacity.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository

class AsteroidWorker(context: Context, params: WorkerParameters):
        CoroutineWorker(context, params) {


    override suspend fun doWork(): Result {

        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database!!)
        return try {

            repository.refreshAsteroids()
            Log.i("Refresh State","Refreshed Successfully")
            Result.success()


        }catch (e:Exception){
            Log.i("Refresh State","Refresh Failed")
            Result.retry()
        }
    }
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

}
