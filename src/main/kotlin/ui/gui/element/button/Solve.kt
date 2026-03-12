package ui.gui.element.button

import androidx.compose.ui.graphics.Color
import ui.basic.Command
import ui.gui.processor.GraphicProcessor

class Solve(
    private val index: Int,
    private val graphicProcessor: GraphicProcessor
) : Button(graphicProcessor, Color(0xFF43A047), "Решить") {
    override fun execute() {
        prepare()
        graphicProcessor.executeCommand(Command.Type.SOLVE,
            Command.Arguments(listOf((index + 1).toString())))
        graphicProcessor.endCommands()
    }
}
