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
    @Query("SELECT * FROM asteroid")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

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