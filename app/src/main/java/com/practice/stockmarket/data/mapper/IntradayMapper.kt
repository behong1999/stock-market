package com.practice.stockmarket.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.practice.stockmarket.data.remote.dto.IntradayInfoDto
import com.practice.stockmarket.domain.model.CompanyInfo
import com.practice.stockmarket.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo() : IntradayInfo {
  val pattern = "yyyy-mm-dd HH:mm:ss"
  val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
  val localDateTime = LocalDateTime.parse(timestamp, formatter)
  return IntradayInfo(localDateTime, close)
}

