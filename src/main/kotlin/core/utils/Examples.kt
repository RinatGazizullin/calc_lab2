package core.utils

import core.model.Expression
import core.parser.ExpressionParser

class Examples {
    companion object {
        private const val EXAMPLE = "2 * x + 1"

        fun quickExample(): Expression {
            return ExpressionParser.parse(EXAMPLE)
        }
    }
}
