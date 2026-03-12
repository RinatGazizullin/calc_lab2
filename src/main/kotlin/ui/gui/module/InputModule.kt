package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.gui.element.button.Solve
import ui.gui.element.input.ExpressionInput
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.processor.GraphicProcessor
import ui.gui.processor.StateManager

class InputModule(
    graphicProcessor: GraphicProcessor,
    stateManager: StateManager,
    index: Int
) {
    private val solve = Solve(index, graphicProcessor)
    private val data = ExpressionInput(stateManager, index)

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
