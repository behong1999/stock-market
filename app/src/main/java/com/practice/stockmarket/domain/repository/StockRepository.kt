package com.practice.stockmarket.domain.repository

import com.practice.stockmarket.domain.model.CompanyInfo
import com.practice.stockmarket.domain.model.CompanyListing
import com.practice.stockmarket.domain.model.IntradayInfo
import com.practice.stockmarket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
  suspend fun getCompanyListings(
    fetchFromRemote: Boolean, //
    query : String
  ): Flow<Resource<List<CompanyListing>>>
  
  suspend fun getIntradayInfo(
    symbol:String
  ): Resource<List<IntradayInfo>>
  
  suspend fun getCompanyInfo(
    symbol : String
  ): Resource<CompanyInfo>
}