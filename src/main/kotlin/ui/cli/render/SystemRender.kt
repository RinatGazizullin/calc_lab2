package ui.cli.render

import core.model.Expression
import core.processor.ExpressionProcessor
import ui.cli.basic.CanRender

class SystemRender(
    private val expressionRender: CanRender<Expression>
) : CanRender<ExpressionProcessor> {
    override fun render(t: ExpressionProcessor): String {
        var counter = 1
        var first = true
        val result = StringBuilder()

        for (expression in t.exps) {
            if (first) first = false else result.appendLine()
            result.append("${counter++}) ${expressionRender.render(expression)}")
        }

        return result.toString()
    }
}
