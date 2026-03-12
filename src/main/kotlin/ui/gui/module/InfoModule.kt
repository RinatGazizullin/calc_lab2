package ui.gui.module

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.processor.ExpressionProcessor
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.processor.GraphicProcessor

class InfoModule(
    expressionProcessor: ExpressionProcessor,
    graphicProcessor: GraphicProcessor
) {
    private val graphModule = GraphModule(expressionProcessor)
    private val resultModule = ResultModule(graphicProcessor)

    @Composable
    fun content() {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .weight(6f)
                    .fillMaxWidth()
                    .padding(PADDING_ROUND),
            ) {
                graphModule.content()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                resultModule.content()
            }
        }
    }
}
