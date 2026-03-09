package core.model

import java.math.BigDecimal
import java.math.MathContext

class Equation(private val equation: SimpleEquation) {
    companion object {
        private val EPSILON = BigDecimal("1E-6")
    }

    fun calculate(param: BigDecimal): BigDecimal {
        return equation.apply(param)
    }

    fun derivative(param: BigDecimal): BigDecimal {
        return (equation.apply(param + EPSILON) - equation.apply(param - EPSILON))
            .divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    fun secondDerivative(x: BigDecimal): BigDecimal {
        val a = derivative(x + EPSILON)
        val b = derivative(x - EPSILON)

        return (a - b) / (EPSILON * BigDecimal.valueOf(2))
    }

    fun interface SimpleEquation {
        fun apply(param: BigDecimal): BigDecimal
    }
}
