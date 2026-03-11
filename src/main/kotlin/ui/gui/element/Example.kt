package ui.gui.element

import androidx.compose.ui.graphics.Color
import core.processor.ExpressionProcessor
import core.utils.Examples
import ui.gui.module.Button

class Example(
    private val index: Int,
    private val expressionProcessor: ExpressionProcessor
) : Button(COLOR, TEXT) {
    companion object {
        private const val TEXT = "Пример"
        private val COLOR = Color(0xFFE53935)
    }

    override fun execute() {
        expressionProcessor.changeExpression(Examples.quickExample(), index)
    }
}
