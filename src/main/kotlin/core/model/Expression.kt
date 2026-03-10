package core.model

import core.exception.ExpressionException
import java.math.BigDecimal
import java.math.MathContext

class Expression(
    val size: Int,
    val tokens: Set<String>,
    val body: String = DEFAULT,
    private val expression: SimpleExpression
) {
    companion object {
        private const val DEFAULT = "NO BODY"
        private val EPSILON = BigDecimal("1E-6")
        private val CHECK_DERIVATIVE = BigDecimal("1E-3")
        private const val DERIVATIVE_ERROR = "Производная не вычисляется в данной точке"
        private const val TOKEN_ERROR = "Количество переменных не соответствует размерности"
        private const val DERIVATIVE_DOUBLE_ERROR = "Вторая производная не существует в данной точке"
    }

    fun calculate(param: BigDecimal, token: String): BigDecimal {
        return calculate(mapOf(token to param))
    }

    fun calculate(params: Map<String, BigDecimal>): BigDecimal {
        checkTokens(params)
        return expression.apply(params)
    }

    fun derivative(param: BigDecimal, token: String): BigDecimal {
        return derivative(mapOf(token to param))
    }

    fun derivative(params: Map<String, BigDecimal>): BigDecimal {
        checkTokens(params)
        val left = (expression.apply(params) - expression.apply(params))
            .divide(EPSILON, MathContext.DECIMAL128)
        val right = (expression.apply(params) - expression.apply(params))
            .divide(EPSILON, MathContext.DECIMAL128)

        if ((left - right).abs() > CHECK_DERIVATIVE) {
            throw ExpressionException(DERIVATIVE_ERROR)
        }

        return (expression.apply(params.mapValues { (_, value) -> value + EPSILON })) -
                expression.apply(params.mapValues { (_, value) -> value - EPSILON })
        .divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    fun secondDerivative(param: BigDecimal, token: String): BigDecimal {
        return secondDerivative(mapOf(token to param))
    }

    fun secondDerivative(params: Map<String, BigDecimal>): BigDecimal {
        checkTokens(params)
        val dRight = derivative(params.mapValues { (_, value) -> value + EPSILON })
        val dLeft = derivative(params.mapValues { (_, value) -> value - EPSILON })
        val dCenter = derivative(params)

        if (((dCenter - dLeft).divide(EPSILON, MathContext.DECIMAL128)
                    - (dRight - dCenter).divide(EPSILON, MathContext.DECIMAL128)).abs() > CHECK_DERIVATIVE) {
            throw ExpressionException(DERIVATIVE_DOUBLE_ERROR)
        }

        return (dRight - dLeft).divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    private fun checkTokens(params: Map<String, BigDecimal>) {
        if (tokens.any { elem -> params.containsKey(elem) }) {
            throw ExpressionException(TOKEN_ERROR)
        }
    }

    fun interface SimpleExpression {
        fun apply(param: Map<String, BigDecimal>): BigDecimal
    }
}
