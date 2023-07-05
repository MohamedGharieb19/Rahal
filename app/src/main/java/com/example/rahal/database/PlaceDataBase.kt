package com.example.rahal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rahal.data.Place
import com.example.rahal.data.createPlans.CreatedPlan
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.example.rahal.data.token.Token


@Database(entities = [Place::class, CreatedPlan::class, PlacesInCreatedPlan::class, Token::class], version = 16)
@TypeConverters(PlaceTypeConverter::class)
abstract class PlaceDataBase: RoomDatabase() {
    abstract fun placeDao():PlaceDao
    abstract fun createdPlanDao(): CreatedPlanDao
    abstract fun placesInCreatedPlanDao(): PlacesInCreatedPlanDao
    abstract fun tokenDao(): TokenDao

}