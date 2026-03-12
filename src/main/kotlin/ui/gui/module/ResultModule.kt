package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.gui.element.button.Show
import ui.gui.element.data.Output
import ui.gui.element.button.SolveAll
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.processor.GraphicProcessor
import ui.gui.processor.StateManager

class ResultModule(
    stateManager: StateManager,
    graphicProcessor: GraphicProcessor
) {
    private val solve = SolveAll(graphicProcessor)
    private val show = Show(graphicProcessor)
    private val data = Output(stateManager)

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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(PADDING_ROUND)
            ) {
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
                    solve.content()
                }
            }
        }
    }
}
