package com.practice.stockmarket.di

import android.app.Application
import androidx.room.Room
import com.practice.stockmarket.data.local.StockDatabase
import com.practice.stockmarket.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Make sure the dependencies live as long as the application does
object AppModule {
  
  @Provides
  @Singleton
  fun provideStockApi() : StockApi {
    return Retrofit.Builder()
      .baseUrl(StockApi.BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
      .create()
  }
  
  @Provides
  @Singleton
  fun provideStockDatabase(context : Application) : StockDatabase {
    return Room.databaseBuilder(context,
      StockDatabase::class.java,
      "stock.db")
      .build()
  }
  
}