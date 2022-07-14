package com.practice.stockmarket.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.stockmarket.domain.repository.StockRepository
import com.practice.stockmarket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
  private val repository : StockRepository
) : ViewModel() {
  
  // Create a new observable State instance
  var state by mutableStateOf(CompanyListingsState())
  
  private var searchJob : Job? = null
  
  init {
    getCompanyListings()
  }
  
  fun onEvent(event : CompanyListingEvent) {
    when (event) {
      is CompanyListingEvent.Refresh -> {
        getCompanyListings(fetchFromRemote = true)
      }
      
      is CompanyListingEvent.OnSearchQueryChange -> {
        state = state.copy(searchQuery = event.query)
        // Cancel the current running coroutine searchJob and relaunch it
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
          // The process begins if there is a stop more than 500ms
          delay(500L)
          getCompanyListings()
        }
      }
    }
  }
  
  private fun getCompanyListings(
    query : String = state.searchQuery.lowercase(),
    fetchFromRemote : Boolean = false
  ) {
    viewModelScope.launch {
      repository
        .getCompanyListings(fetchFromRemote, query)
        // Collect the flow
        .collect { result ->
          when (result) {
            is Resource.Loading -> {
              state = state.copy(isLoading = result.isLoading)
            }
            
            is Resource.Error -> Unit
            
            is Resource.Success -> {
              result.data?.let { listings ->
                state = state.copy(companies = listings)
              }
            }
          }
        }
    }
  }
}