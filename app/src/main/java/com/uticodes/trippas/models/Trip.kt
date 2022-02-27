package com.uticodes.trippas.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "trips_table")
data class Trip (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val departure: String,
    val departure_date: String,
    val departure_time: String,
    val destination: String,
    val destination_date: String,
    val destination_time: String,
    val trip_type: String,
): Parcelable
