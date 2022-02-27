package com.uticodes.trippas.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uticodes.trippas.models.Trip
import com.uticodes.trippas.models.database.TripDB
import com.uticodes.trippas.models.database.TripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    val getTrips: LiveData<List<Trip>>
    private val repository: TripRepository

    init {
        val tripDAO = TripDB.getDatabase(application).tripDao()
        repository = TripRepository(tripDAO)
        getTrips = repository.getTrips
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.addTrip(trip)
        }
    }


    fun updateTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.updateTrip(trip)
        }
    }

    fun deleteTrip(trip: Trip){
        viewModelScope.launch(Dispatchers.IO){

            repository.deleteTrip(trip)
        }
    }
}