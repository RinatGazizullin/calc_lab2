package core.model

import java.math.BigDecimal
import java.math.MathContext

class Equation(private val equation: SimpleEquation) {
    companion object {
        private val EPSILON = BigDecimal(10E-6)
    }

    fun calculate(param: BigDecimal): BigDecimal {
        return equation.apply(param)
    }

    fun derivative(param: BigDecimal): BigDecimal {
        return (equation.apply(param.plus(EPSILON)) - equation.apply(param.minus(EPSILON)))
            .divide(EPSILON.multiply(BigDecimal.valueOf(2)), MathContext.DECIMAL64)
    }

    fun interface SimpleEquation {
        fun apply(param: BigDecimal): BigDecimal
    }
}
