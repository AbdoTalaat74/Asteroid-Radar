package com.udacity.asteroidradar.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface DatabaseDao{
    @Query("SELECT * FROM asteroid ORDER BY close_approach_date ASC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid WHERE close_approach_date= :date ")
    fun getCurrentDayAsteroids(date: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid WHERE close_approach_date BETWEEN :fromDate AND :toDate ORDER BY close_approach_date ASC")
    fun getWeeklyAsteroids(fromDate: String, toDate: String): LiveData<List<Asteroid>>


    @Insert(onConflict = REPLACE)
    fun insert( asteroid: Asteroid)
}

@Dao
interface PicDatabaseDao{
    @Query("SELECT * FROM pic_of_day")
    fun getPicture(): LiveData<PictureOfDay>

    @Insert(onConflict = REPLACE)
    fun insertPicture( pictureOfDay: PictureOfDay)
}