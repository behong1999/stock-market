package com.learning.stockmarket.data.csv

import java.io.InputStream

interface CSVParser <T>{
  suspend fun parse(inputStream : InputStream):List<T>
}