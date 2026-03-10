package ui.cli.builder

import core.exception.BuilderException
import core.exception.ExpressionException
import core.model.Expression
import core.parser.ExpressionParser
import ui.cli.basic.CanBuild
import ui.cli.basic.CanBuild.Companion.MAX_COUNT
import ui.cli.processor.InterfaceProcessor

class ExpressionBuilder(
    private val interfaceProcessor: InterfaceProcessor,
) : CanBuild<Expression> {
    companion object {
        private const val EXAMPLE = "f(...) = "
        private const val INTRO_MESSAGE = "Введите функцию!"
        private const val BUILDER_ERROR = "Не удалось получить новую функцию"
    }

    override fun build(size: Int): Expression {
        for (i in 1..MAX_COUNT) {
            interfaceProcessor.renderMessage(INTRO_MESSAGE)
            try {
                interfaceProcessor.renderMessage(EXAMPLE, false)
                return ExpressionParser.parse(interfaceProcessor.readLine())
            } catch (e: ExpressionException) {
                interfaceProcessor.renderError(e.message!!)
            }
        }
        throw BuilderException(BUILDER_ERROR)
    }
}
