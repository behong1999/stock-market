package com.learning.stockmarket.data.repository

import com.learning.stockmarket.data.local.StockDatabase
import com.learning.stockmarket.data.mapper.toCompanyListing
import com.learning.stockmarket.data.remote.dto.StockApi
import com.learning.stockmarket.domain.model.CompanyListing
import com.learning.stockmarket.domain.repository.StockRepository
import com.learning.stockmarket.utils.Resource
import com.learning.stockmarket.utils.Resource.Loading
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepoImpl @Inject constructor(
  val api : StockApi,
  val db : StockDatabase
) : StockRepository {
  
  private val dao = db.dao
  
  override suspend fun getCompanyListings(
    fetchFromRemote : Boolean,
    query : String
  ) : kotlinx.coroutines.flow.Flow<Resource<List<CompanyListing>>> {
    return flow {
      emit(Loading())
      
      val localListings = dao.searchCompanyListing(query)
      
      emit(Resource.Success(
        data = localListings.map { it.toCompanyListing() }
      ))
      
      val isDbEmpty = localListings.isEmpty() && query.isBlank()
      val loadFromCacheOnly = !isDbEmpty && !fetchFromRemote
      
      if (loadFromCacheOnly) {
        emit(Loading(false))
        return@flow // Return to label function which is "flow"
      }
      
      val remoteListings = try{
        val response = api.getListings()
        val csvReader = CSVReader(InputStreamReader(response.byteStream()))
        
      }catch (e:IOException){
        e.printStackTrace()
        emit(Resource.Error("Failed to Load Data"))
      }catch (e: HttpException){
        emit(Resource.Error("Failed to Load Data because of HTTP exception"))
        
      }
    }
    
  }
}