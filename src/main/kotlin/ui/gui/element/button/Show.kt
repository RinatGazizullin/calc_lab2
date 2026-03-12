package ui.gui.element.button

import androidx.compose.ui.graphics.Color
import ui.gui.processor.GraphicProcessor

class Show(
    private val graphicProcessor: GraphicProcessor
) : Button(graphicProcessor, Color(0xFFF9A825), "Показать") {
    override fun execute() {
        prepare()
        graphicProcessor.endCommands()
    }
}
