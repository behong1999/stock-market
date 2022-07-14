package com.practice.stockmarket.presentation.company_listings

import com.practice.stockmarket.domain.model.CompanyListing

data class CompanyListingsState (
  val companies : List<CompanyListing> = emptyList(),
  val isLoading: Boolean = false,
  val isRefreshing: Boolean = false,
  val searchQuery:String = "",
  )
