package com.learning.stockmarket.domain.repository

import androidx.room.Query
import com.learning.stockmarket.data.local.StockDao
import com.learning.stockmarket.domain.model.CompanyListing
import com.learning.stockmarket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
  suspend fun getCompanyListings(
    fetchFromRemote: Boolean, //
    query : String
  ): Flow<Resource<List<CompanyListing>>>
}