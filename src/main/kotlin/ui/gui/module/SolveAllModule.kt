package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND

class SolveAllModule {
    private val solve = Button(Color(0xFF43A047), "Решить все")
    private val show = Button(Color(0xFFF9A825), "Показать обоих")

    @Composable
    fun content() {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING_ROUND),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                solve.content()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                show.content()
            }
        }
    }
}
