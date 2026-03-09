package core.solver

import core.basic.SingleSolver
import core.model.Equation
import java.math.BigDecimal

class HalfSolver : SingleSolver {
    override val name: String = "Метод половинного деления"

    companion object {
        private fun calculateMiddle(a: BigDecimal, b: BigDecimal): BigDecimal {
            return (a + b) / BigDecimal.valueOf(2)
        }
    }

    override fun solve(
        equation: Equation,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal
    ): BigDecimal {
        verify(equation, left, right)
        var a = left
        var b = right
        var x = calculateMiddle(a, b)

        do {
            if (equation.calculate(x) * equation.calculate(b) < BigDecimal.ZERO) {
                a = x
            } else {
                b = x
            }
            x = calculateMiddle(a, b)
        } while ((equation.calculate(a) - equation.calculate(b)).abs() < epsilon)

        return x
    }
}
