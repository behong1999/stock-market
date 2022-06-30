package com.learning.stockmarket.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

interface StockDao {
  
  @Insert(onConflict = REPLACE)
  suspend fun insertCompanyListings(
    companyListingEntities : List<CompanyListingEntity>
  )
  
  @Query("Delete From companylistingentity")
  suspend fun clearCompanyListings()
  
  @Query("""
    Select *
    From companylistingentity
    Where LOWER(name)
    Like '%' || LOWER(:query) || '%'
    Or UPPER(:query) == symbol
  """
  )
  suspend fun searchCompanyListing(query : String) : List<CompanyListingEntity>
}