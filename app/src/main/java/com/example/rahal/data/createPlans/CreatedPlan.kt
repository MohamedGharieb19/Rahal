package com.example.rahal.data.createPlans

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "created_plan_table")
@Parcelize
data class CreatedPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val image: String,
    val text: String,
    val list: List<PlacesInCreatedPlan>
):Parcelable
