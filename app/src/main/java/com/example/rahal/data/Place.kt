package com.example.rahal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class Place(
    val __v: Int,
    @PrimaryKey
    val _id: String,
    val activityDesctiptor: List<String>,
    val description: String,
    //val id: String,
    var image: String? = "https://media-cdn.tripadvisor.com/media/photo-o/09/60/28/be/nino-s-italian-restaurant.jpg",
    val location: Location,
    val name: String,
    val num_reviews: Int,
    val numberOfReviews: Int,
    val rating: Double
)