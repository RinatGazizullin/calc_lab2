package core.model

import core.exception.EquationException
import java.math.BigDecimal
import java.math.MathContext

class Equation(private val equation: SimpleEquation) {
    companion object {
        private val EPSILON = BigDecimal("1E-6")
        private val CHECK_DERIVATIVE = BigDecimal("1E-3")
        private const val DERIVATIVE_ERROR = "Производная не вычисляется в данной точке"
        private const val DERIVATIVE_DOUBLE_ERROR = "Вторая производная не существует в данной точке"
    }

    fun calculate(param: BigDecimal): BigDecimal {
        return equation.apply(param)
    }

    fun derivative(param: BigDecimal): BigDecimal {
        val left = (equation.apply(param + EPSILON) - equation.apply(param))
            .divide(EPSILON, MathContext.DECIMAL128)
        val right = (equation.apply(param) - equation.apply(param - EPSILON))
            .divide(EPSILON, MathContext.DECIMAL128)

        if ((left - right).abs() > CHECK_DERIVATIVE) {
            throw EquationException(DERIVATIVE_ERROR)
        }

        return (equation.apply(param + EPSILON) - equation.apply(param - EPSILON))
            .divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    fun secondDerivative(x: BigDecimal): BigDecimal {
        val dRight = derivative(x + EPSILON)
        val dLeft = derivative(x - EPSILON)
        val dCenter = derivative(x)

        val diffLeft = (dCenter - dLeft).divide(EPSILON, MathContext.DECIMAL128)
        val diffRight = (dRight - dCenter).divide(EPSILON, MathContext.DECIMAL128)

        if ((diffLeft - diffRight).abs() > CHECK_DERIVATIVE) {
            throw EquationException(DERIVATIVE_DOUBLE_ERROR)
        }

        return (dRight - dLeft).divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    fun interface SimpleEquation {
        fun apply(param: BigDecimal): BigDecimal
    }
}
