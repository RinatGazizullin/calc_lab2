package core.parser

import com.ezylang.evalex.Expression
import com.ezylang.evalex.parser.ParseException
import com.ezylang.evalex.parser.Token
import core.exception.EquationException
import core.model.System
import java.math.BigDecimal

class EquationParser {
    companion object {
        private const val FORMAT_ERROR = "Неверный формат строки"
    }

    fun parse(equation: String): System.MultiEquation {
        try {
            val eval = Expression(equation.replace(",", "."))
            val names = mutableListOf<String>()

            for (node in eval.allASTNodes) {
                if (node.token.type == Token.TokenType.VARIABLE_OR_CONSTANT &&
                    !eval.constants.containsKey(node.token.value)) {
                    val name = node.token.value

                    if (!names.contains(name)) {
                        names.add(name)
                    }
                }
            }

            val localNames = names.toList()
            return System.MultiEquation { params: List<BigDecimal> ->
                val expression = eval.copy()

                localNames.forEachIndexed { index, name ->
                    expression.with(name, params[index])
                }

                expression.evaluate().numberValue
            }
        } catch (e: ParseException) {
            throw EquationException(FORMAT_ERROR)
        }
    }
}
