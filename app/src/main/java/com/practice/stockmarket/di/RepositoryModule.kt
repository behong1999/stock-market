package com.practice.stockmarket.di

import com.practice.stockmarket.data.csv.CSVParser
import com.practice.stockmarket.data.csv.CompanyListingParser
import com.practice.stockmarket.data.csv.IntradayInfoParser
import com.practice.stockmarket.data.repository.StockRepoImpl
import com.practice.stockmarket.domain.model.CompanyListing
import com.practice.stockmarket.domain.model.IntradayInfo
import com.practice.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  
  @Binds
  @Singleton
  abstract fun bindCompanyListingsParser(
    companyListingParser : CompanyListingParser
  ) : CSVParser<CompanyListing>
  
  @Binds
  @Singleton
  abstract fun bindIntradayInfoParser(
    intradayInfoParser : IntradayInfoParser
  ) : CSVParser<IntradayInfo>
  
  @Binds
  @Singleton
  abstract fun bindStockRepository(
    stockRepoImpl : StockRepoImpl
  ) : StockRepository
}