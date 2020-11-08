package com.geofferyj.trippas.models.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.geofferyj.trippas.models.Trip

@Dao
interface TripDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrip(trip: Trip)

    @Update
    suspend fun  updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Query(" SELECT * FROM trips_table ORDER BY id DESC")
    fun getTrips(): LiveData<List<Trip>>
}
