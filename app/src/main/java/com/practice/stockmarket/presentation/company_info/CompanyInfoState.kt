package com.practice.stockmarket.presentation.company_info

import com.practice.stockmarket.domain.model.CompanyInfo
import com.practice.stockmarket.domain.model.IntradayInfo

data class CompanyInfoState(
  val intradayInfos : List<IntradayInfo> = emptyList(),
  val company : CompanyInfo? = null,
  val isLoading : Boolean = false,
  val error: String? = null
  )