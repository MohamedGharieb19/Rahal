package com.example.rahal.data.suggestedPlans

data class Location(
    val address: String,
    val coordinates: List<Double> ? = emptyList(),
    val type: String
)