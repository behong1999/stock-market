package com.practice.stockmarket.domain.repository

import com.practice.stockmarket.domain.model.CompanyListing
import com.practice.stockmarket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
  suspend fun getCompanyListings(
    fetchFromRemote: Boolean, //
    query : String
  ): Flow<Resource<List<CompanyListing>>>
}