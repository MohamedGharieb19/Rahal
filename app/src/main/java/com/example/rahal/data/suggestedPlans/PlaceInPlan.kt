package com.example.rahal.data.suggestedPlans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PlaceInPlan(
    val __v: Int,
    val _id: String,
    //val activityDesctiptor: List<String>,
    //val cuisine: List<String>,
    val description: String ? = "",
    //val hotelClass: Int,
    //val id: String,
    val image : String ? = "",
    val location: @RawValue Location,
    val name: String,
    val numberOfReviews: Int? = 0,
    //val phone: String,
    //val priceLevel: String,
    val rating: Double
):Parcelable