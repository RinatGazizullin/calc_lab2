package core.model

import core.exception.EquationException
import java.math.BigDecimal
import java.math.MathContext

class System(private val equations: List<MultiEquation>) {
    val size = this.equations.size

    companion object {
        private val EPSILON = BigDecimal("1E-6")
        private val CHECK_DERIVATIVE = BigDecimal("1E-3")
        private const val DERIVATIVE_ERROR = "Производная не вычисляется в данной точке"
        private const val DERIVATIVE_DOUBLE_ERROR = "Вторая производная не существует в данной точке"
    }

    fun calculate(params: List<BigDecimal>): Vector {
        val result = Vector.empty(size)
        IntRange(0, size).forEach { index -> result.results[index] = calculateByIndex(params, index) }
        return result
    }

    private fun calculateByIndex(param: List<BigDecimal>, index: Int): BigDecimal {
        return equations[index].apply(param)
    }

    fun derivative(param: List<BigDecimal>): Vector {
        val result = Vector.empty(size)
        IntRange(0, size).forEach { index -> result.results[index] = derivativeByIndex(param, index) }
        return result
    }

    private fun derivativeByIndex(param: List<BigDecimal>, index: Int): BigDecimal {
        val left = (equations[index].apply(param + EPSILON) - equations[index].apply(param))
            .divide(EPSILON, MathContext.DECIMAL128)
        val right = (equations[index].apply(param) - equations[index].apply(param - EPSILON))
            .divide(EPSILON, MathContext.DECIMAL128)

        if ((left - right).abs() > CHECK_DERIVATIVE) {
            throw EquationException(DERIVATIVE_ERROR)
        }

        return (equations[index].apply(param + EPSILON) - equations[index].apply(param - EPSILON))
            .divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    fun secondDerivative(param: List<BigDecimal>): Vector {
        val result = Vector.empty(size)
        IntRange(0, size).forEach { index -> result.results[index] = secondDerivativeByIndex(param, index) }
        return result
    }

    private fun secondDerivativeByIndex(param: List<BigDecimal>, index: Int): BigDecimal {
        val dRight = derivativeByIndex(param + EPSILON, index)
        val dLeft = derivativeByIndex(param - EPSILON, index)
        val dCenter = derivativeByIndex(param, index)

        if (((dCenter - dLeft).divide(EPSILON, MathContext.DECIMAL128)
                    - (dRight - dCenter).divide(EPSILON, MathContext.DECIMAL128)).abs() > CHECK_DERIVATIVE) {
            throw EquationException(DERIVATIVE_DOUBLE_ERROR)
        }

        return (dRight - dLeft).divide(EPSILON * BigDecimal.valueOf(2), MathContext.DECIMAL128)
    }

    fun interface MultiEquation {
        fun apply(param: List<BigDecimal>): BigDecimal
    }
}