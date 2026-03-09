package core.basic

import core.exception.EquationException
import core.model.Equation
import java.math.BigDecimal
import java.math.MathContext

interface SingleSolver {
    val name: String

    companion object {
        private const val STEPS: Long = 100
        private const val ENOUGH_ERROR = "Достаточное условие невыполнено"
        private const val NECESSARY_ERROR = "Необходимое условие невыполнено"
        private const val LEFT_ERROR = "Левая граница не может быть больше правой"
    }

    fun solve(equation: Equation, left: BigDecimal, right: BigDecimal, epsilon: BigDecimal): BigDecimal

    fun verify(equation: Equation, left: BigDecimal, right: BigDecimal) {
        if (left >= right) {
            throw EquationException(LEFT_ERROR)
        }

        if (equation.calculate(left).multiply(equation.calculate(right)) >= BigDecimal.ZERO) {
            throw EquationException(NECESSARY_ERROR)
        }

        val step = right.add(left.negate()).divide(BigDecimal.valueOf(STEPS), MathContext.DECIMAL128)

        for (i in 0..STEPS) {
            val pre = equation.derivative(left.add(step.multiply(BigDecimal.valueOf(i)))) >= BigDecimal.ZERO
            val now = equation.derivative(left.add(step.multiply(BigDecimal.valueOf(i + 1)))) >= BigDecimal.ZERO

            if (pre != now) {
                throw EquationException(ENOUGH_ERROR)
            }
        }
    }
}
