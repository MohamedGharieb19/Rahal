package com.example.rahal.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.rahal.data.Location
import com.example.rahal.data.createPlans.PlacesInCreatedPlan
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

@TypeConverters
class PlaceTypeConverter {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }


    @TypeConverter
    fun fromDatabaseValue(value: String?): Location? {
        return value?.let { json ->
            val jsonObject = JSONObject(json)
            val address = jsonObject.optString("address")
            val coordinatesJsonArray = jsonObject.optJSONArray("coordinates")
            val coordinates = mutableListOf<Double>()
            for (i in 0 until coordinatesJsonArray.length()) {
                coordinates.add(coordinatesJsonArray.getDouble(i))
            }
            val type = jsonObject.optString("type")
            Location(address, coordinates, type)
        }
    }

    @TypeConverter
    fun toDatabaseValue(location: Location?): String? {
        return location?.let { location ->
            val jsonObject = JSONObject()
            jsonObject.put("address", location.address)
            val coordinatesJsonArray = JSONArray(location.coordiantes)
            jsonObject.put("coordinates", coordinatesJsonArray)
            jsonObject.put("type", location.type)
            jsonObject.toString()
        }
    }

    @TypeConverter
    fun fromJson(json: String): List<PlacesInCreatedPlan> {
        val type = object : TypeToken<List<PlacesInCreatedPlan>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(list: List<PlacesInCreatedPlan>): String {
        return Gson().toJson(list)
    }


}