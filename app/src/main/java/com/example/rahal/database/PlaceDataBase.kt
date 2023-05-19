package com.example.rahal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rahal.data.Place


@Database(entities = [Place::class], version = 3)
@TypeConverters(PlaceTypeConverter::class)
abstract class PlaceDataBase: RoomDatabase() {
    abstract fun placeDao():PlaceDao
}