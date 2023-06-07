package com.example.rahal.data.suggestedPlans

data class Plan(
    val image: String,
    val name: String,
    val places: List<PlaceInPlan>
)