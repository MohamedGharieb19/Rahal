package com.example.rahal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rahal.data.Place


@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(place: Place)

    @Delete
    suspend fun delete(place: Place)

    @Query("SELECT * FROM place")
    fun getFavorites(): LiveData<List<Place>>
}