package com.example.rahal.data.token

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "token")
data class Token(
    @PrimaryKey
    val token: String
)
