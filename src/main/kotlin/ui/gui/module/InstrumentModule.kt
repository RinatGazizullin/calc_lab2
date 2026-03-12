package ui.gui.module

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.gui.processor.GraphicProcessor
import ui.gui.processor.StateManager

class InstrumentModule(
    graphicProcessor: GraphicProcessor,
    stateManager: StateManager
) {
    private val input1 = InputModule(graphicProcessor, stateManager, 0)
    private val input2 = InputModule(graphicProcessor, stateManager, 1)
    private val borderModule = BorderModule(stateManager)

    @Composable
    fun content() {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                input1.content()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                input2.content()
            }

            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                borderModule.content()
            }
        }
    }
}
