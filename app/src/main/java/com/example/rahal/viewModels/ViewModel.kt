package com.example.rahal.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rahal.data.Place
import com.example.rahal.data.search.City
import com.example.rahal.data.suggestedPlans.PlaceInPlan
import com.example.rahal.data.suggestedPlans.Plan
import com.example.rahal.remove2.Restaurant
import com.example.rahal.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    val repository: Repository
): ViewModel(){

    private val _getRecommendedMutableLiveData = MutableLiveData<List<Place>>()
    val getRecommendedLiveData: LiveData<List<Place>> = _getRecommendedMutableLiveData

    private val _getRecommendedForSpecificCityMutableLiveData = MutableLiveData<List<Place>>()
    val getRecommendedForSpecificCityLiveData: LiveData<List<Place>> = _getRecommendedForSpecificCityMutableLiveData

    private val _getTopRatedForSpecificCityMutableLiveData = MutableLiveData<List<Place>>()
    val getTopRatedForSpecificCityLiveData: LiveData<List<Place>> = _getTopRatedForSpecificCityMutableLiveData

    private val _getTopRatedMutableLiveData = MutableLiveData<List<Place>>()
    val getTopRatedLiveData: LiveData<List<Place>> = _getTopRatedMutableLiveData

    private val _getNewMutableLiveData = MutableLiveData<List<Restaurant>>()
    val getNewLiveData: LiveData<List<Restaurant>> = _getNewMutableLiveData

     val _getActivitiesMutableLiveData = MutableLiveData<List<String>>()
    val getActivitiesLiveData: LiveData<List<String>> = _getActivitiesMutableLiveData

    private val _getContentActivitiesMutableLiveData = MutableLiveData<List<Place>>()
    val getContentActivitiesLiveData: LiveData<List<Place>> = _getContentActivitiesMutableLiveData

    private val _getSearchMutableLiveData = MutableLiveData<List<City>>()
    val getSearchLiveData: LiveData<List<City>> = _getSearchMutableLiveData

    private val _getRecommendedPlansMutableLiveData = MutableLiveData<List<Plan>>()
    val getRecommendedPlansLiveData: LiveData<List<Plan>> = _getRecommendedPlansMutableLiveData

    private val _getRecommendedViewPlansMutableLiveData = MutableLiveData<List<PlaceInPlan>>()
    val getRecommendedViewPlansLiveData: LiveData<List<PlaceInPlan>> = _getRecommendedViewPlansMutableLiveData
    fun getRecommended(limit:String){
        viewModelScope.launch {
            try {
                val response = repository.getRecommended(limit)

                response.body()!!.data.docuemnts.let {
                    _getRecommendedMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getRecommended")
            }
        }
    }

    fun getRecommendedForSpecificCity(cityName: String){
        viewModelScope.launch {
            try {
                val response = repository.getRecommendedForSpecificCity(cityName)

                response.body()!!.data.attractions.let {
                    _getRecommendedForSpecificCityMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getRecommended")
            }
        }
    }

    fun getTopRated(limit:String){
        viewModelScope.launch {
            try {
                val response = repository.getTopRated(limit)

                response.body()!!.data.docuemnts.let {
                    _getTopRatedMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getTopRated")
            }
        }
    }

    fun getTopRatedForSpecificCity(cityName: String){
        viewModelScope.launch {
            try {
                val response = repository.getTopRatedForSpecificCity(cityName)

                response.body()!!.data.attractions.let {
                    _getTopRatedForSpecificCityMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getRecommended")
            }
        }
    }

    fun getSearch(searchQuery: String){
        viewModelScope.launch {
            try {
                val response = repository.getSearch(searchQuery)

                response.body()!!.data.cities.let {
                    _getSearchMutableLiveData.postValue(it)
                }
            }catch (t:Throwable){

            }
        }
    }

    fun getContentOfActivities(cityName: String,type: String){
        viewModelScope.launch {
            try {
                val response = repository.getContentOfActivities(cityName,type)

                response.body()!!.data.attractions.let {
                    _getContentActivitiesMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getContentOfActivities")
            }
        }
    }

    fun getNew(cityName:String){
        viewModelScope.launch {
            try {
                val response = repository.getNew(cityName,"10")

                response.body()!!.data.restaurants.let {
                    _getNewMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Error getNew")
            }
        }
    }

    fun getActivities(cityName: String){
        viewModelScope.launch {
            try {
                val response = repository.getActivities(cityName)

                response.body()!!.data.activityTypes.let {
                    _getActivitiesMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){

            }
        }
    }

    fun getRecommendedPlans(){
        viewModelScope.launch {
            try {

                val response = repository.getRecommendedPlans()

                response.body()!!.data.plans.let {
                    _getRecommendedPlansMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Errorss getRecommendedPlans")
            }
        }
    }

    fun getRecommendedPlanss(){
        viewModelScope.launch {
            try {

                val response = repository.getRecommendedPlanss()

                response.body()!!.data.plans[0].places.let {
                    _getRecommendedViewPlansMutableLiveData.postValue(it)
                }

            }catch (t:Throwable){
                Log.d("testApp",t.message.toString()+ " Errorss getRecommendedPlans")
            }
        }
    }

    fun upsert(place: Place) = viewModelScope.launch {
        repository.upsert(place)
    }

    fun delete(place: Place) = viewModelScope.launch {
        repository.delete(place)
    }

    fun getFavorites() = repository.getFavoritesPlaces

}