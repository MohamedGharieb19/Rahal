package com.example.rahal.data.suggestedPlans

data class PlaceInPlan(
    val __v: Int,
    val _id: String,
    val activityDesctiptor: List<String>,
    val cuisine: List<String>,
    val description: String,
    val hotelClass: Int,
    val id: String,
    val image: String,
    val location: Location,
    val name: String,
    val numberOfReviews: Int,
    val phone: String,
    val priceLevel: String,
    val rating: Double
)