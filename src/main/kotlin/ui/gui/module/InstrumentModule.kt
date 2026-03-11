package ui.gui.module

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.processor.ExpressionProcessor
import ui.gui.processor.GraphicProcessor

class InstrumentModule(
    expressionProcessor: ExpressionProcessor,
    graphicProcessor: GraphicProcessor
) {
    private val input1 = InputModule(expressionProcessor, graphicProcessor, FIRST)
    private val input2 = InputModule(expressionProcessor, graphicProcessor, SECOND)
    private val output = ResultModule()

    companion object {
        private const val FIRST = 0
        private const val SECOND = 1
    }

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
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                output.content()
            }
        }
    }
}
