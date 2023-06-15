package com.example.rahal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rahal.data.createPlans.CreatedPlan


@Dao
interface CreatedPlanDao {
    @Insert
    suspend fun insert(parent: CreatedPlan): Long

    @Update
    suspend fun updateCreatedPlan(createdPlan: CreatedPlan)

    @Delete
    suspend fun deleteCreatedPlan(createdPlan: CreatedPlan)

    @Query("SELECT * FROM created_plan_table")
    fun getCreatedPlans(): LiveData<List<CreatedPlan>>

//
}

