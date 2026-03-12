package core.utils

import core.model.Expression
import core.parser.ExpressionParser

class Examples {
    companion object {
        private val EXAMPLES = listOf(
            "2 * x + 1",
            "x + 5",
            "3 * x - 7 - 1/y",
            "x^2 - 4",
            "x^2 + 2*x + 1 + y",
            "x^2 - 3*x + 2",
            "2*x - 4",
            "5 - x",
            "x/2 + 1 - y",
        )

        fun quickExample(): Expression {
            return ExpressionParser.parse(EXAMPLES.random())
        }
    }
}
