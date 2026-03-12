package ui.gui.element.button

import androidx.compose.ui.graphics.Color
import ui.basic.Command
import ui.gui.processor.GraphicProcessor

class SolveAll(
    private val graphicProcessor: GraphicProcessor
) : Button(graphicProcessor, Color(0xFF43A047), "Решить все") {
    override fun execute() {
        prepare()
        graphicProcessor.executeCommand(Command.Type.SOLVE)
        graphicProcessor.endCommands()
    }
}
