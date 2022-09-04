package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroid")
@JsonClass(generateAdapter = true)
data class Asteroid(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Long,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val codename: String,

    @ColumnInfo(name ="close_approach_data" )
    @SerializedName("close_approach_data")
    val closeApproachDate: String,

    @ColumnInfo(name = "absolute_magnitude_h")
    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    @SerializedName("estimated_diameter")
    val estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity")
    @SerializedName("relative_velocity")
    val relativeVelocity: Double,

    @ColumnInfo(name = "miss_distance")
    @SerializedName("miss_distance")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean

) : Parcelable

//id (Not for displaying but for using in db)
//absolute_magnitude @
//estimated_diameter_max (Kilometers)@
//is_potentially_hazardous_asteroid@
//close_approach_data -> relative_velocity -> kilometers_per_second
//close_approach_data -> miss_distance -> astronomical