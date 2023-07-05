package com.example.rahal.data.token

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "token")
data class Token(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val token: String
)
