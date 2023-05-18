package com.example.rahal.api

import com.example.rahal.data.Data
import com.example.rahal.data.ForgetPasswordRequest
import com.example.rahal.data.PlaceList
import com.example.rahal.data.RegisterUserRequest
import com.example.rahal.data.UserRequest
import com.example.rahal.data.UserResponse
import com.example.rahal.data.activites.Activities
import com.example.rahal.data.activites.ActivitiesTypes
import com.example.rahal.data.activitiesContent.Content
import com.example.rahal.data.search.Search
import com.example.rahal.data.search.SearchList
import com.example.rahal.remove2.List
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    @POST("users/login")
     fun login(
        @Body userRequest: UserRequest
    ): Call<UserResponse>
    @POST("users/signup")
    fun signup(
        @Body registerUserRequest: RegisterUserRequest
    ): Call<UserResponse>
    @POST("users/forgetpassword")
    fun forgetpassword(
        @Body forgetPasswordRequest: ForgetPasswordRequest
    )





    @GET("attractions")
    suspend fun getAttractions(
        @Query("limit") sort: String
    ): Response<PlaceList>

    @GET("cities/city/{city}/attractions")
    suspend fun getAttractionsForSpecificCity(
        @Path("city") city: String,
    ): Response<Content>




    @GET("cities/city/{city}/restaurants")
    suspend fun getNew(
        @Path("city") city: String,
        @Query("limit") sort: String
    ): Response<List>

    @GET("cities/activties/city/{city}/attractions")
    suspend fun getActivities(
        @Path("city") city: String
    ): Response<Activities>

    @GET("cities/activties/city/{city}/activity/{type}/attractions")
    suspend fun getContentOfActivites(
        @Path("city") city: String,
        @Path("type") type: String
    ): Response<Content>

    @GET("cities/search")
    suspend fun searchForPlaces(
        @Query("search") searchQuery: String
    ): Response<Search>
}