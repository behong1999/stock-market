package com.practice.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import com.practice.stockmarket.presentation.company_listings.NavGraphs
import com.practice.stockmarket.ui.theme.StockMarketTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  
  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      StockMarketTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
        ) {
          DestinationsNavHost(navGraph = NavGraphs.root)
        }
      }
    }
  }
}
