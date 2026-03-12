package core.model

import core.exception.ExpressionException
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class Expression(
    val size: Int,
    val tokens: Set<String>,
    private val expression: SimpleExpression,
    val body: String = DEFAULT
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
        return derivative(mapOf(token to param), token)
    }

    fun derivative(params: Map<String, BigDecimal>, token: String): BigDecimal {
        checkTokens(params)
        val left = (expression.apply(params) - expression
            .apply(params.mapValues { (key, value) -> if (key == token) value - EPSILON else value }))
            .divide(EPSILON, MathContext(40, RoundingMode.HALF_UP))
        val right = (expression.apply(params.mapValues {  (key, value) ->
            if (key == token) value + EPSILON else value }) - expression.apply(params))
            .divide(EPSILON, MathContext(40, RoundingMode.HALF_UP))

        if ((left - right).abs() > CHECK_DERIVATIVE) {
            throw ExpressionException(DERIVATIVE_ERROR)
        }

        return (expression.apply(params.mapValues {  (key, value) ->
            if (key == token) value + EPSILON else value }) -
                expression.apply(params.mapValues  { (key, value) ->
                    if (key == token) value - EPSILON else value }))
            .divide(
                EPSILON * BigDecimal.valueOf(2),
                MathContext(40, RoundingMode.HALF_UP)
            )
    }

    fun secondDerivative(param: BigDecimal, token: String): BigDecimal {
        return secondDerivative(mapOf(token to param), token)
    }

    fun secondDerivative(params: Map<String, BigDecimal>, token: String): BigDecimal {
        checkTokens(params)
        val dRight = derivative(params.mapValues {  (key, value) ->
            if (key == token) value + EPSILON else value  }, token)
        val dLeft = derivative(params.mapValues {  (key, value) ->
            if (key == token) value - EPSILON else value  }, token)
        val dCenter = derivative(params, token)

        if (((dCenter - dLeft).divide(
                EPSILON, MathContext(
                    40, RoundingMode
                        .HALF_UP
                )
            ) - (dRight - dCenter).divide(
                EPSILON, MathContext(
                    40,
                    RoundingMode.HALF_UP
                )
            )).abs() > CHECK_DERIVATIVE
        ) {
            throw ExpressionException(DERIVATIVE_DOUBLE_ERROR)
        }

        return (dRight - dLeft).divide(
            EPSILON * BigDecimal.valueOf(2),
            MathContext(40, RoundingMode.HALF_UP)
        )
    }

    private fun checkTokens(params: Map<String, BigDecimal>) {
        if (tokens.any { elem -> !params.containsKey(elem) }) {
            println(params)
            throw ExpressionException(TOKEN_ERROR)
        }
    }

    fun interface SimpleExpression {
        fun apply(param: Map<String, BigDecimal>): BigDecimal
    }
}
