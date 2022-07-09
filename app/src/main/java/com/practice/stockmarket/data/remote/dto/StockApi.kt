package com.practice.stockmarket.data.remote.dto

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query



interface StockApi {
  
  // Listing and Delisting status
  @GET("query?function=LISTING_STATUS")
  suspend fun getListings(
    @Query("apikey") apiKey:String = API_KEY
  ) : ResponseBody
  
  companion object{
    const val API_KEY = "UL94AL58B12DJHVC"
    const val BASE_URL = "https://www.alphavantage.co"
  }
}