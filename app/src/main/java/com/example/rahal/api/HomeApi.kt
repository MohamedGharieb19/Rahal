package com.example.rahal.api

import com.example.rahal.data.Data
import com.example.rahal.data.PlaceList
import com.example.rahal.data.activites.Activities
import com.example.rahal.data.activites.ActivitiesTypes
import com.example.rahal.data.activitiesContent.Content
import com.example.rahal.remove2.List
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    @GET("attractions")
    suspend fun getAttractions(
        @Query("limit") sort: String
    ): Response<PlaceList>

    @GET("cities/city/{city}/restaurants")
    suspend fun getNew(
        @Path("city") city: String,
        @Query("limit") sort: String
    ): Response<List>

    @GET("cities/city/{city}/activties")
    suspend fun getActivities(
        @Path("city") city: String
    ): Response<Activities>

    @GET("cities/city/{city}/activties/{type}/attractions")
    suspend fun getContentOfActivites(
        @Path("city") city: String,
        @Path("type") type: String
    ): Response<Content>
}