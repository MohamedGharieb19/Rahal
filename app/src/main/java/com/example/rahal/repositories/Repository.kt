package com.example.rahal.repositories

import android.util.Log
import com.example.rahal.api.HomeApi
import com.example.rahal.data.Place
import com.example.rahal.data.PlaceList
import com.example.rahal.data.activites.Activities
import com.example.rahal.data.activitiesContent.Content
import com.example.rahal.data.createPlans.CreatedPlan
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.example.rahal.data.search.Search
import com.example.rahal.data.suggestedPlans.suggestedPlans
import com.example.rahal.database.PlaceDataBase
import com.example.rahal.remove2.List
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val homeApi: HomeApi,
    private val placeDataBase: PlaceDataBase
) {

    private val databaseFavorites = placeDataBase.placeDao()
    val getFavoritesPlaces = databaseFavorites.getFavorites()

    private val databaseCreatedPlan = placeDataBase.createdPlanDao()
    val getCreatedPlans = databaseCreatedPlan.getCreatedPlans()

    private val databasePlacesInCreatedPlan = placeDataBase.placesInCreatedPlanDao()
    //val getPlacesInCreatedPlan = databasePlacesInCreatedPlan.getPlacesInCreatedPlan()


    suspend fun insertCreatedPlan(createdPlan: CreatedPlan){
        databaseCreatedPlan.insert(createdPlan)
    }

    suspend fun insertPlacesInCreatedPlan(placesInCreatedPlan: PlacesInCreatedPlan){
        databasePlacesInCreatedPlan.insert(placesInCreatedPlan)
    }

    suspend fun upsert(place: Place){
        databaseFavorites.upsert(place)
    }

    suspend fun delete(place: Place){
        databaseFavorites.delete(place)
    }

    suspend fun getSearch(searchQuery: String): Response<Search>{
        val response = homeApi.searchForPlaces(searchQuery)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getSearch() : ${response.body()}")
        }else {
            Log.d("TestApp","Failed to connected getSearch(): ${response.code()}")
        }
        return response
    }

    suspend fun getRecommended(sort: String): Response<PlaceList>{
        val response = homeApi.getAttractions(sort)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getAttractions() : ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected getAttractions(): ${response.code()}")
        }
        return response
    }

    suspend fun getRecommendedForSpecificCity(cityName: String): Response<Content>{
        val response = homeApi.getAttractionsForSpecificCity(cityName)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getAttractions() : ${response.body()}")
        }else {
            Log.d("TestApp","Failed to connected getAttractions(): ${response.code()}")
        }
        return response
    }

    suspend fun getTopRated(sort: String): Response<PlaceList>{
        val response = homeApi.getAttractions(sort)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getAttractions() : ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected getAttractions(): ${response.code()}")
        }
        return response
    }

    suspend fun getTopRatedForSpecificCity(cityName: String): Response<Content>{
        val response = homeApi.getAttractionsForSpecificCity(cityName)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getAttractions() : ${response.body()}")
        }else {
            Log.d("TestApp","Failed to connected getAttractions(): ${response.code()}")
        }
        return response
    }

    suspend fun getContentOfActivities(cityName: String,type:String): Response<Content>{
        val response = homeApi.getContentOfActivites(cityName,type)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getContentOfActivities() : ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected getContentOfActivities(): ${response.code()}")
        }
        return response
    }

    suspend fun getNew(cityName:String,sort: String): Response<List>{
        val response = homeApi.getNew(cityName,sort)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getcites() : ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected getcites(): ${response.code()}")
        }
        return response
    }

    suspend fun getActivities(city: String): Response<Activities>{
        val response = homeApi.getActivities(city)
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getActivities() : ${response.code()}")
        }else {
            Log.d("TestApp","Failed to connected getActivities(): ${response.code()}")
        }
        return response
    }
    private var savedText: String? = null


    fun saveCity(text: String) {
        savedText = text
    }

    fun getSavedCity(): String? {
        return savedText
    }

    suspend fun getRecommendedPlans(): Response<suggestedPlans> {
        val response = homeApi.recommendedPlans()
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getRecommended() : ${response.code()}")
            Log.d("TestApp","Success to connect getRecommended() : ${response.body()}")
        }else {
            Log.d("TestApp","Failed to connected getRecommended(): ${response.code()}")
        }
        return response
    }

    suspend fun getRecommendedPlanss(): Response<suggestedPlans> {
        val response = homeApi.recommendedPlanss()
        if (response.isSuccessful){
            Log.d("TestApp","Success to connect getRecommended() : ${response.code()}")
            Log.d("TestApp","Success to connect getRecommended() : ${response.body()}")
        }else {
            Log.d("TestApp","Failed to connected getRecommended(): ${response.code()}")
        }
        return response
    }

}