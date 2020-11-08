package com.geofferyj.trippas.models.database

import androidx.lifecycle.LiveData
import com.geofferyj.trippas.models.Trip

class TripRepository(private val tripDAO: TripDAO) {

    val getTrips: LiveData<List<Trip>> = tripDAO.getTrips()

    suspend fun addTrip(trip: Trip){
        tripDAO.addTrip(trip)
    }

    suspend fun updateTrip(trip: Trip){
        tripDAO.updateTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip){
        tripDAO.deleteTrip(trip)
    }
}