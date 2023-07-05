package com.example.rahal.module

import android.app.Application
import android.provider.DocumentsContract.Root
import androidx.room.Room
import com.example.rahal.api.HomeApi
import com.example.rahal.database.CreatedPlanDao
import com.example.rahal.database.PlaceDao
import com.example.rahal.database.PlaceDataBase
import com.example.rahal.database.PlacesInCreatedPlanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

//    @Provides
//    @Singleton
//    fun provideApi(): HomeApi =
//        Retrofit.Builder()
//            .baseUrl("http://rahalaa-production.up.railway.app/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideApi(): HomeApi =
        Retrofit.Builder()
            .baseUrl("http://172.20.10.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    //192.168.1.63
    //192.168.1.9
    @Provides
    @Singleton
    fun provideDataBase(application: Application): PlaceDataBase =
        Room.databaseBuilder(application,PlaceDataBase::class.java,"place.database")
            .fallbackToDestructiveMigration()
            .build()

}