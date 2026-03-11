package core.solver

import core.basic.SingleSolver
import core.model.Expression
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class HalfSolver : SingleSolver {
    override val name: String = "Метод половинного деления"

    companion object {
        private fun calculateMiddle(a: BigDecimal, b: BigDecimal): BigDecimal {
            return (a + b).divide(BigDecimal.valueOf(2), MathContext(40, RoundingMode.HALF_UP))
        }
    }

    override fun solve(
        expression: Expression,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal,
        token: String
    ): BigDecimal {
        verify(expression, left, right, token)
        var a = left
        var b = right
        var x = calculateMiddle(a, b)

        do {
            if (expression.calculate(x, token).signum()
                * expression.calculate(b, token).signum() < 0) {
                a = x
            } else {
                b = x
            }
            x = calculateMiddle(a, b)
        } while ((a - b).abs() > epsilon)

        return x
    }
}
