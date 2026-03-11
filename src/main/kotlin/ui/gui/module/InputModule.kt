package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.processor.ExpressionProcessor
import ui.gui.element.Input
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.processor.GraphicProcessor

class InputModule(
    expressionProcessor: ExpressionProcessor,
    graphicProcessor: GraphicProcessor,
    index: Int
) {
    private val solve = Button(Color(0xFF43A047), "Решить")
    private val show = Button(Color(0xFFF9A825), "Показать")
    private val delete = Button(Color(0xFFE53935), "Пример")
    private val data = Input(expressionProcessor, graphicProcessor, index)

    @Composable
    fun content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING_ROUND),
            verticalArrangement = Arrangement.spacedBy(PADDING_ROUND)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    // .background(Color.DarkGray)
            ) {
                data.content()
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PADDING_ROUND)
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

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    delete.content()
                }
            }
        }
    }
}
