package ui.gui.element.button

import androidx.compose.ui.graphics.Color
import ui.gui.processor.GraphicProcessor

class Show(
    private val index: Int,
    private val graphicProcessor: GraphicProcessor
) : Button(Color(0xFFF9A825), "Показать") {
    override fun execute() {}
}
