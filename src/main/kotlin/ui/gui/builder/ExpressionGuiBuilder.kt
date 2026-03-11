package ui.gui.builder

import core.exception.BuilderException
import core.exception.ExpressionException
import core.model.Border
import core.model.Expression
import core.parser.ExpressionParser
import ui.basic.CanBuild
import ui.basic.CanBuild.Companion.MAX_COUNT
import ui.cli.processor.InterfaceProcessor

class ExpressionGuiBuilder(
    private val interfaceProcessor: InterfaceProcessor,
) : CanBuild<Expression> {
    companion object {
        private const val EXAMPLE = "(...) = "
        private const val INTRO_MESSAGE = "Введите функцию!"
        private const val BUILDER_ERROR = "Не удалось получить новую функцию"
    }

    override fun build(tokens: Set<String>): Expression {
        TODO("Not et implemented")
    }
}
