package com.learning.stockmarket.data.mapper

import com.learning.stockmarket.data.local.CompanyListingEntity
import com.learning.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing {
  return CompanyListing(
    name = name,
    symbol = symbol,
    exchange = exchange
  )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity{
  return CompanyListingEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
  )
}

