package core.solver

import core.basic.SingleSolver
import core.exception.EnumException
import core.model.Equation
import java.math.BigDecimal
import java.math.MathContext

class IterationSolver : SingleSolver {
    override val name: String = "Метод простой итерации"

    companion object {
        private const val STEPS: Long = 100
        private val CHECK_DERIVATIVE = BigDecimal("1E-3")
        private const val ENOUGH_ERROR = "Достаточное условие невыполнено"
    }

    override fun solve(
        equation: Equation,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal
    ): BigDecimal {
        verify(equation, left, right)

        val step = right.add(left.negate()).divide(BigDecimal.valueOf(STEPS), MathContext.DECIMAL128)
        val maxDerivative = LongRange(0, STEPS).maxOf { index ->
            equation.derivative(left + step * BigDecimal.valueOf(index)).abs() }
        val lambda = if (equation.calculate(left) > BigDecimal.ZERO)
            -BigDecimal.ONE / maxDerivative else BigDecimal.ONE / maxDerivative

        val phi = Equation { x: BigDecimal -> x + lambda * equation.calculate(x) }
        val q = LongRange(0, STEPS).maxOf { index ->
            phi.derivative(left + step * BigDecimal.valueOf(index)).abs() }

        if (q - BigDecimal.ONE >= CHECK_DERIVATIVE) {
            throw EnumException(ENOUGH_ERROR)
        }

        // var x = if (equation.calculate(left) * equation.secondDerivative(left) > BigDecimal.ZERO) left else right
        var x = (left + right) / BigDecimal.valueOf(2)
        var new = phi.calculate(x)

        do {
            x = new
            new = phi.calculate(x)
        } while ((x - new).abs() > epsilon)

        return new
    }
}
