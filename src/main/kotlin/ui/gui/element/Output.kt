package ui.gui.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import ui.gui.processor.ApplicationProcessor

class Output {
    @Composable
    fun content(
        value: String = "",
        modifier: Modifier = Modifier,
        placeholder: String = "Результат..."
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(ApplicationProcessor.PADDING_ROUND)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isNotEmpty()) {
                Text(
                    text = value,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            } else {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
