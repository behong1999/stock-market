package com.practice.stockmarket.data.repository

import com.practice.stockmarket.data.csv.CSVParser
import com.practice.stockmarket.data.local.StockDatabase
import com.practice.stockmarket.data.mapper.toCompanyListing
import com.practice.stockmarket.data.mapper.toCompanyListingEntity
import com.practice.stockmarket.data.remote.dto.StockApi
import com.practice.stockmarket.domain.model.CompanyListing
import com.practice.stockmarket.domain.repository.StockRepository
import com.practice.stockmarket.utils.Resource

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

// Include all the Interfaces
@Singleton
class StockRepoImpl @Inject constructor(
  val api : StockApi,
  db : StockDatabase,
  val parser : CSVParser<CompanyListing>
) : StockRepository {
  
  private val dao = db.dao
  
  override suspend fun getCompanyListings(
    fetchFromRemote : Boolean,
    query : String
  ) : kotlinx.coroutines.flow.Flow<Resource<List<CompanyListing>>> {
    return flow {
      emit(Resource.Loading(true))
      
      val localListings = dao.searchCompanyListing(query)
      
      emit(Resource.Success(
        data = localListings.map { it.toCompanyListing() }
      ))
      
      // Fetch data from cache
      val isDbEmpty = localListings.isEmpty() && query.isBlank()
      val loadFromCacheOnly = !isDbEmpty && !fetchFromRemote
      
      if (loadFromCacheOnly) {
        emit(Resource.Loading(false))
        return@flow // Return to label function which is "flow"
      }
      
      // Fetch remote data
      val remoteListings = try {
        val response = api.getListings()
        parser.parse(response.byteStream())
        
      } catch (e : IOException) {
        e.printStackTrace()
        emit(Resource.Error("Failed to Load Data"))
        null
      } catch (e : HttpException) {
        emit(Resource.Error("Failed to Load Data because of HTTP exception"))
        null
      }
      
      // Update local DB
      remoteListings?.let { listings ->
        emit(Resource.Success(
          data = dao.searchCompanyListing("").map { it.toCompanyListing() }
        ))
        emit(Resource.Loading(false))
        
        dao.clearCompanyListings()
        dao.insertCompanyListings(
          listings.map { it.toCompanyListingEntity() }
        )
      }
    }
  }
}