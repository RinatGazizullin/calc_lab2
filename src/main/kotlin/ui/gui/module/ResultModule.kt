package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.gui.element.data.Output
import ui.gui.element.button.SolveAll
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.processor.GraphicProcessor

class ResultModule(graphicProcessor: GraphicProcessor) {
    private val solve = SolveAll(graphicProcessor)
    private val data = Output()

    @Composable
    fun content() {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING_ROUND),
            horizontalArrangement = Arrangement.spacedBy(PADDING_ROUND)
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                // .background(Color.DarkGray)
            ) {
                data.content()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                solve.content()
            }
        }
    }
}
