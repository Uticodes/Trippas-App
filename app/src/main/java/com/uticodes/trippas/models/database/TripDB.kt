package com.uticodes.trippas.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uticodes.trippas.models.Trip


@Database(entities = [Trip::class], version = 1, exportSchema = false)
abstract class TripDB: RoomDatabase() {

    abstract fun tripDao(): TripDAO

    companion object{
        @Volatile
        private var INSTANCE: TripDB? = null

        fun getDatabase(context: Context): TripDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDB ::class.java,
                    "trips_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}