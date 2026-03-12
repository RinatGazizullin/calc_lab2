package ui.gui.builder

import core.exception.BuilderException
import core.exception.ExpressionException
import core.model.Expression
import core.parser.ExpressionParser
import ui.basic.CanBuild
import ui.gui.processor.StateManager

class ExpressionGuiBuilder(
    private val stateManager: StateManager,
) : CanBuild<Expression, Pair<Int, Set<String>>> {
    override fun build(data: Pair<Int, Set<String>>): Expression {
        try {
            return ExpressionParser.parse(stateManager.inputs[data.first])
        } catch (e: ExpressionException) {
            throw BuilderException(e.message!!)
        }
    }
}
