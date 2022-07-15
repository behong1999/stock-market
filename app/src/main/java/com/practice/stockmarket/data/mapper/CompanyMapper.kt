package com.practice.stockmarket.data.mapper

import com.practice.stockmarket.data.local.CompanyListingEntity
import com.practice.stockmarket.data.remote.dto.CompanyInfoDto
import com.practice.stockmarket.domain.model.CompanyInfo
import com.practice.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing {
  return CompanyListing(
    name = name,
    symbol = symbol,
    exchange = exchange
  )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity {
  return CompanyListingEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
  )
}

fun CompanyInfoDto.toCompanyInfo() : CompanyInfo {
  return CompanyInfo(
    symbol = symbol ?: "",
    description = description ?: "",
    name = name ?: "",
    country = country ?: "",
    industry = industry ?: ""
  )
}

