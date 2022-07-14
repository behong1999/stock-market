package com.practice.stockmarket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = Purple200,
  primaryVariant = Purple700,
  secondary = Teal200,
  background = DarkBlue
)

@Composable
fun StockMarketTheme(content : @Composable () -> Unit) {
  
  MaterialTheme(
    colors = DarkColorPalette,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}