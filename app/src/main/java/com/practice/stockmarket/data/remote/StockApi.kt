package com.practice.stockmarket.data.remote

import com.practice.stockmarket.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
  
  // Listing and Delisting status
  @GET("query?function=LISTING_STATUS")
  suspend fun getListings(
    @Query("apikey") apiKey:String = API_KEY
  ) : ResponseBody
  
  @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
  suspend fun getIntradayInfo(
    @Query("symbol") symbol:String,
    @Query("apikey") apiKey:String = API_KEY
  ):ResponseBody
  
  // Get Json Data and parse it to CompanyInfoDto
  @GET("function=OVERVIEW")
  suspend fun getCompanyInfo(
    @Query("symbol") symbol:String,
    @Query("apikey") apiKey:String = API_KEY
  ) : CompanyInfoDto
  
  companion object{
    const val API_KEY = "UL94AL58B12DJHVC"
    const val BASE_URL = "https://www.alphavantage.co"
  }
}