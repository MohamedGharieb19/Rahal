package com.example.rahal.data.createPlans

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rahal.data.Location
import kotlinx.parcelize.Parcelize


@Entity(tableName = "places_created_plan_table")
@Parcelize
data class PlacesInCreatedPlan(
    @PrimaryKey(autoGenerate = true)
    val __id: Long = 0,
    val parentId: Long,
    val __v: Int,
    val _id: String,
    val activityDesctiptor: List<String>,
    val cuisine: List<String>,
    val description: String,
    val hotelClass: Int,
//    val id: String,
    val image: String,
    //val location: Location,
    val name: String,
    val numberOfReviews: Int,
    val phone: String,
    val priceLevel: String,
    val rating: Double
):Parcelable
