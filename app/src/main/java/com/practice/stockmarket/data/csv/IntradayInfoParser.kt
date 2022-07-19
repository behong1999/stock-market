package com.practice.stockmarket.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import com.practice.stockmarket.data.mapper.toIntradayInfo
import com.practice.stockmarket.data.remote.dto.IntradayInfoDto
import com.practice.stockmarket.domain.model.CompanyInfo
import com.practice.stockmarket.domain.model.IntradayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor(
) :CSVParser<IntradayInfo> {
  
  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun parse(inputStream : InputStream) : List<IntradayInfo> {
    val csvReader = CSVReader(InputStreamReader(inputStream))
    return withContext(Dispatchers.IO){
      csvReader.readAll()
        .drop(1) // Drop the first row because the first row only shows the columns' titles
        .mapNotNull { line ->
          val timestamp = line.getOrNull(0) ?:return@mapNotNull null
          val close = line.getOrNull(4) ?:return@mapNotNull null
          val dto = IntradayInfoDto(timestamp,close.toDouble())
          dto.toIntradayInfo()
        }
        .filter {
          // Intraday Info of Yesterday
          it.date.dayOfMonth == LocalDateTime.now().minusDays(4).dayOfMonth
        }
        .sortedBy {
          it.date.hour
        }
        .also {
          csvReader.close()
        }
    }
  }
}