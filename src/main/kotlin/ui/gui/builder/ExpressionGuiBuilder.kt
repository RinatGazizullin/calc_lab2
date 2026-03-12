package ui.gui.builder

import core.model.Expression
import ui.basic.CanBuild
import ui.gui.processor.DataProcessor

class ExpressionGuiBuilder(
    dataProcessor: DataProcessor
) : CanBuild<Expression> {
    override fun build(tokens: Set<String>): Expression {
        TODO("Not et implemented")
    }
}
