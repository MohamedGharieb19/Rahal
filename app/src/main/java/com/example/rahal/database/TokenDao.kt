package com.example.rahal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rahal.data.token.Token

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(token: Token)

    @Query("SELECT * FROM token\n" +
            "ORDER BY token DESC\n" +
            "LIMIT 1;")
    fun getToken(): LiveData<Token>
}