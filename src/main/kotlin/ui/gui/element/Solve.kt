package ui.gui.element

import androidx.compose.ui.graphics.Color
import ui.basic.Command
import ui.gui.module.Button
import ui.gui.processor.GraphicProcessor

class Solve(
    private val index: Int,
    private val graphicProcessor: GraphicProcessor
) : Button(Color(0xFF43A047), "Решить") {
    override fun execute() {
        println(graphicProcessor.executeCommand(Command.Type.SOLVE,
            Command.Arguments(listOf(index.toString()))).message)
    }
}