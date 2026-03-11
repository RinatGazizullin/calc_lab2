package ui.gui.element

import androidx.compose.ui.graphics.Color
import ui.basic.Command
import ui.gui.module.Button
import ui.gui.processor.GraphicProcessor

class Example(
    private val index: Int,
    private val graphicProcessor: GraphicProcessor
) : Button(COLOR, TEXT) {
    companion object {
        private const val TEXT = "Пример"
        private val COLOR = Color(0xFFE53935)
    }

    override fun execute() {
        println(
            graphicProcessor.executeCommand(Command.Type.EXAMPLE,
            Command.Arguments(listOf((index + 1).toString()))).message
        )
    }
}
