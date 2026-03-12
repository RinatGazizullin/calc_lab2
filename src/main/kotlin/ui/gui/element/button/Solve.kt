package ui.gui.element.button

import androidx.compose.ui.graphics.Color
import ui.gui.processor.GraphicProcessor

class Solve(
    private val index: Int,
    private val graphicProcessor: GraphicProcessor
) : Button(Color(0xFF43A047), "Решить") {
    override fun execute() {}
}
