package core.parser

import com.ezylang.evalex.Expression
import com.ezylang.evalex.config.ExpressionConfiguration
import com.ezylang.evalex.config.ExpressionConfiguration.ExpressionConfigurationBuilder
import com.ezylang.evalex.parser.ParseException
import core.exception.ExpressionException
import core.utils.TextUtils
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class ExpressionParser {
    companion object {
        private const val FORMAT_ERROR = "Неверный формат строки"
        private val config = ExpressionConfiguration.builder()
            .mathContext(MathContext(34, RoundingMode.HALF_UP))
            .build()

        fun parse(equation: String): core.model.Expression {
            try {
                val prepared = TextUtils.prepareOnly(equation)
                val eval = Expression(prepared, config)
                val localNames = eval.usedVariables.toSet()
                return core.model.Expression(localNames.size, localNames, prepared
                ) { params: Map<String, BigDecimal> ->
                    val expression = eval.copy()
                    params.keys.forEach { key -> expression.with(key, params[key]) }
                    expression.evaluate().numberValue
                }
            } catch (e: ParseException) {
                throw ExpressionException(FORMAT_ERROR)
            }
        }
    }
}
