package ui.gui.module

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.processor.ExpressionProcessor
import java.math.BigDecimal

class GraphModule(private val expressionProcessor: ExpressionProcessor) {
    @Composable
    fun content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "График",
                color = Color.Green
            )
        }
    }
}
