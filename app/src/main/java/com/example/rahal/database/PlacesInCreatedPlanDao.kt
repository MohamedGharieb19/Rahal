package com.example.rahal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rahal.data.createPlans.PlacesInCreatedPlan

@Dao
interface PlacesInCreatedPlanDao {
    @Insert
    suspend fun insert(placesInCreatedPlan: List<PlacesInCreatedPlan>): List<Long>

    @Query("SELECT * FROM places_created_plan_table WHERE parentId = :parentId")
    fun getPlacesInCreatedPlan(parentId: Long): LiveData<List<PlacesInCreatedPlan>>
}