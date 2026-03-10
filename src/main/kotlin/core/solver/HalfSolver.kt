package core.solver

import core.basic.SingleSolver
import core.model.Expression
import java.math.BigDecimal

class HalfSolver : SingleSolver {
    override val name: String = "Метод половинного деления"

    companion object {
        private fun calculateMiddle(a: BigDecimal, b: BigDecimal): BigDecimal {
            return (a + b) / BigDecimal.valueOf(2)
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
            if (expression.calculate(x, token) * expression.calculate(b, token) < BigDecimal.ZERO) {
                a = x
            } else {
                b = x
            }
            x = calculateMiddle(a, b)
        } while ((expression.calculate(a, token) - expression.calculate(b, token)).abs() > epsilon)

        return x
    }
}
