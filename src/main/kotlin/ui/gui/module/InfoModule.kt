package ui.gui.module

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.processor.ExpressionProcessor
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.processor.GraphicProcessor
import ui.gui.processor.StateManager

class InfoModule(
    expressionProcessor: ExpressionProcessor,
    graphicProcessor: GraphicProcessor,
    stateManager: StateManager
) {
    private val graphModule = GraphModule(expressionProcessor, stateManager)
    private val resultModule = ResultModule(stateManager, graphicProcessor)

    @Composable
    fun content() {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth()
                    .padding(PADDING_ROUND),
            ) {
                graphModule.content()
            }

            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                resultModule.content()
            }
        }
    }
}
