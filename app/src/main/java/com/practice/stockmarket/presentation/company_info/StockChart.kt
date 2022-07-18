package com.practice.stockmarket.presentation.company_info

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.stockmarket.domain.model.IntradayInfo
import kotlin.math.round
import kotlin.math.roundToInt

@SuppressLint("NewApi")
@Composable
fun StockChart(
  infos : List<IntradayInfo> = emptyList(),
  modifier : Modifier = Modifier,
  graphColor : Color = Color.Green
) {
  val spacing = 100f
  
  // Half transparent
  val blurredGraphColor = remember {
    graphColor.copy(alpha = 0.5f)
  }
  
  // ------------------------------- Y-axis -------------------------------
  val upperBound = remember {
    (infos.maxOfOrNull { it.close })?.plus(1)?.roundToInt() ?: 0
  }
  
  val lowerBound = remember {
    infos.minOfOrNull { it.close }?.roundToInt() ?: 0
  }
  
  val closeStep = (upperBound - lowerBound) / 5f
  
  // ------------------------------- X-axis -------------------------------
  val density = LocalDensity.current
  
  val textPaint = remember {
    Paint().apply {
      color = android.graphics.Color.WHITE
      textAlign = Paint.Align.CENTER
      textSize = density.run { 12.sp.toPx() }
    }
  }
  
  Canvas(modifier = modifier) {
    val spacePerHour = (size.width - spacing) / infos.size
    (0 until infos.size - 1 step 2).forEach { i ->
      val info = infos[i]
      val hour = info.date.hour
      
      // Access to native canvas
      drawContext.canvas.nativeCanvas.apply {
        drawText(
          hour.toString(),
          spacing + i * spacePerHour,
          size.height - 5,
          textPaint
        )
      }
    }
    
    (0..5).forEach { i ->
      drawContext.canvas.nativeCanvas.apply {
        drawText(
          round(lowerBound + closeStep * i).toString(),
          30f,
          size.height - spacing - i * size.height / 5f,
          textPaint
        )
      }
    }
    var lastX = 0f
    val strokePath = Path().apply {
      val height = size.height
      for (i in infos.indices) {
        val info = infos[i]
        val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
        
        // Height Percentages of 2 Coordinates compared to full height of the canvas
        val leftRatio = (info.close - lowerBound) / (upperBound - lowerBound)
        val rightRatio = (nextInfo.close - lowerBound) / (upperBound - lowerBound)
        
        val x1 = spacing + i * spacePerHour
        val y1 = height - spacing - (leftRatio * height).toFloat()
        val x2 = spacing + (i + 1) * spacePerHour
        val y2 = height - spacing - (rightRatio * height).toFloat()
        
        if (i == 0) {
          moveTo(x1, y1)
        }
        lastX = (x1 + x2) / 2f
        quadraticBezierTo(x1, y1, lastX, (y1 + y2) / 2f)
      }
    }
    
    val fillPath = android.graphics.Path(strokePath.asAndroidPath())
      .asComposePath() // Convert it back to Compose Path
      .apply {
        // The line starts from the last x coordinate of the stroke path
        lineTo(lastX, size.height - spacing)
        lineTo(spacing, size.height - spacing)
        close()
      }
    
    drawPath(
     strokePath,
      graphColor,
      style = Stroke(
        width = 3.dp.toPx(),
        cap = StrokeCap.Round
      )
    )
    drawPath(
      fillPath,
      brush = Brush.verticalGradient(
        colors = listOf(blurredGraphColor, Color.Transparent),
        endY = size.height - spacing
      )
    )
  }
}